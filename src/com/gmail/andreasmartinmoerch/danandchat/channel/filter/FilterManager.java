package com.gmail.andreasmartinmoerch.danandchat.channel.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import com.gmail.andreasmartinmoerch.danandchat.channel.Channel;
import com.gmail.andreasmartinmoerch.danandchat.channel.filter.noargs.FilterWithoutArgs_inChannel;
import com.gmail.andreasmartinmoerch.danandchat.channel.filter.withargs.FilterWithArgs;
import com.gmail.andreasmartinmoerch.danandchat.channel.filter.withargs.FilterWithArgs_hasName;
import com.gmail.andreasmartinmoerch.danandchat.channel.filter.withargs.FilterWithArgs_inGroup;
import com.gmail.andreasmartinmoerch.danandchat.channel.filter.withargs.FilterWithArgs_inGroups;
import com.gmail.andreasmartinmoerch.danandchat.channel.filter.withargs.FilterWithArgs_inWorld;
import com.gmail.andreasmartinmoerch.danandchat.channel.filter.withargs.FilterWithArgs_inWorlds;

public class FilterManager {
	public static final int JOIN = 0;
	public static final int RECEIVE = 1;
	public static final int SEND = 2;
	public static final int[] actions = {JOIN, RECEIVE, SEND};
	
	private Channel channel;
	private List<List<Filter>> filterGroups;
	private List<Player> toFilter;
	private List<Player> filtered;
	private boolean include;
	
	/**
	 * 
	 * @param filterGroups
	 * @param toFilter player list to filter.
	 * @param include true = if player passes a filtergroup, put it in the filtered list. setting this to false will cause it to be unstable fttb.
	 */
	public FilterManager(Channel channel, List<List<Filter>> filterGroups, boolean include){
		this.channel = channel;
		this.filterGroups = filterGroups;;
		if (include){
			filtered = new ArrayList<Player>();
		} else {
			filtered =  toFilter;
		}
		
		this.include = include;
	}
	
	public List<Player> getFiltered(List<Player> toFilter){
		this.toFilter = toFilter;
		
		// Iterates all the players to filter
		playerfilter:
			for (Player player: toFilter){
				
				// Iterates the different types of filter combinations
				filtergroups:
				for (List<Filter> filterGroup: filterGroups){
					// Is set after checking every filter. If turning false while checking filters, the check will stop, and plugin will try next filtergroup.
					boolean passAll = true;
					filters:
					// Iterates the filters in the filtergroup
					for (Filter filter: filterGroup){
						filter.setChannel(channel);
						if (!filter.filterPlayer(player)){
							passAll = false;
							break filters;
						}
						
					}
					// Has passed all tests.
					if (passAll){
						if (include){
							filtered.add(player);
						} else {
							filtered.remove(player);
						}
						continue playerfilter;
					}
				}
			}
	
		return filtered;	
	}
	
	/////////////////STATIC\\\\\\\\\\\\\\\\\\\\\\\\\
	public static HashMap<String, Class<?>> filters = new HashMap<String, Class<?>>(){{
		put("inChannel", FilterWithoutArgs_inChannel.class);
		put("hasName", FilterWithArgs_hasName.class);
		put("inGroup", FilterWithArgs_inGroup.class);
		put("inGroups", FilterWithArgs_inGroups.class);
		put("inWorld", FilterWithArgs_inWorld.class);
		put("inWorlds", FilterWithArgs_inWorlds.class);
	}};

	// TODO: A fucking mess. Comment or make it easier to understand!
	public static List<List<Filter>> makeFilterGroups(List<Object> list){
		List<List<Filter>> toReturn = new ArrayList<List<Filter>>(); 
		// Filters with args
		if (list == null){
			return null;
		}
		// The list should be like this: List<Map<String, List<String>>>
		// First we check if the objects are maps. 
		filtergroups:
			for (Object filtergroup: list){
				
				// This is the map we'll test.
				Map<?, ?> testMap;
				if (filtergroup instanceof Map<?, ?>){
					// Object is a map!
					testMap = (Map<?, ?>)filtergroup;
				} else {
					// Object is not a map, continue iterating filtergroups.
					continue filtergroups;
				}
				
				// Save this for later, we'll add filters to it, once we've found some valid ones.
				List<Filter> filtersInFiltergroup = new ArrayList<Filter>();
				
				// So far so good, now we'll check if the keyset is containing Strings!
				keys:
					for (Object key: testMap.keySet()){
						//Test String
						String testString;
						if (key instanceof String){
							// The key is a String!
							testString = (String)key;
						} else {
							// Key is not a string, continue iterating keys. 
							continue keys;
						}
						// All right so we've got the key. This is the name of the filter. The value to this key should be a List<String>. First we'll test if
						// it's a list.
						
						//Test List<?>
						List<?> testList;
						// This is the value we'll test.
						Object valueOfKey = testMap.get(testString);
						if (valueOfKey instanceof List<?>){
							// It's a list! Parse it to testList.
							testList = (List<?>)valueOfKey;
						} else {
							// It's not a list. Continue iterating keys.
							continue keys;
						}
						
						// We'll add values to this list, once we've found some validated ones. This array is specific to this filter.
						List<String> values = new ArrayList<String>();
						
						// Great, now we've found out that the original list looks like this:
						// List<Map<String, List<?>>>
						// See? Only that last question mark to go.
						values:
							// Iterate the values of testList (which is the List<?>) 
							for (Object value: testList){
								// Test String
								String valueTestString;
								if (value instanceof String){
									// Yay it's a String! Let's parse it.
									valueTestString = (String) value;
								} else {
									// It's not a String. Keep iterating values!
									continue values;
								}
								
								// Now we've got a value of a filter of a filtergroup. Let's add the value to values, so we can keep looking for valid values.
								values.add(valueTestString);
							}
						
						// Great! We've got a filter name and some values. Let's try and mix them up.
						// Let's check if there is a filter with this name.
						if (filters.keySet().contains(testString)){
							// It's an existing filter.
							Class<?> filterClass = filters.get(testString);
							Filter filter;
							try {
								filter = (Filter)filterClass.newInstance();
							} catch (Exception e){
								e.printStackTrace();
								continue keys;
							}
							
							if (filter instanceof FilterWithArgs){
								filter.setValues(values);
							}
							
							filtersInFiltergroup.add(filter);
							
						}
					}
				toReturn.add(filtersInFiltergroup);
			}
		return toReturn;
		
//		filtergroups:
//		for (Object o: list){
//			// Get list
//			List<?> listValue = null;
//			if (o instanceof List<?>){
//				listValue = (List<?>)o;
//			} else {
//				continue filtergroups;
//			}
//			filtergroup:
//			for (Object oo: listValue){
//				List<Filter> filterGroup = new ArrayList<Filter>();
//				List<?> iiListValue = null;
//				if (oo instanceof List<?>){
//					iiListValue = (List<?>)oo;
//				} else {
//					continue filtergroup;
//				}
//				filtersInGroup:
//				for (Object ooo: iiListValue){
//					Map<?, ?> mapValue = null;
//					if (oo instanceof Map<?,?>){
//						mapValue = (Map<?,?>)ooo;
//					} else {
//						continue filtersInGroup;
//					}
//					filtername:
//					for (Object key: mapValue.keySet()){
//						String stringValue = null;
//						if (key instanceof String){
//							stringValue = (String)key;
//						} else {
//							continue filtername;
//						}
//						List<?> listInMap = null;
//						if (mapValue.get(stringValue) instanceof List<?>){
//							listInMap = (List<?>)listInMap;
//						} else {
//							continue filtername;
//						}
//						List<String> filterValues = new ArrayList<String>();
//						Class<?> filterClass = filters.get(stringValue);
//						Filter filter;
//						try {
//							filter = (Filter)filterClass.newInstance();
//						} catch (Exception e){
//							e.printStackTrace();
//							continue filtersInGroup;
//						}
//						
//						if (filter instanceof FilterWithoutArgs){
//							filterGroup.add(filter);
//						} else if (filter instanceof FilterWithArgs){
//							filtervalues:
//							for (Object listInKey: listInMap){
//								if (listInKey instanceof String){
//									filterValues.add((String)listInKey);
//								}
//							}
//							filterGroup.add(filter);
//						}
//					}
//				}
//				toReturn.add(filterGroup);
//			}
//		}
	}
}
