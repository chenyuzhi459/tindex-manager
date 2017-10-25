package io.sugo.zookeeper;

import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by chenyuzhi on 17-10-24.
 */
public class ZookeeperItem {
	public static final String NONE_DATA = "None";
	private final String path;
	private String data;
	private List<Map<String,String>> children;

	public ZookeeperItem(String path) {
		this.path = path;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setChildren(List<Map<String, String>> children) {
		this.children = children;
	}

	public void addChild(Map<String,String> child){
		if(null == children){
			children = Lists.newArrayList();
		}
		this.children.add(child);
	}

	public boolean removeChild(String childKey){
		for(Iterator<Map<String,String>> it =children.iterator();it.hasNext();){
			Map<String,String> child = it.next();
			if(child.get(childKey) != null){
				it.remove();
				return true;
			}
		}
		return false;
	}

	public String getPath() {
		return path;
	}

	public void clearChild(){
		for(Iterator<Map<String,String>> it =children.iterator();it.hasNext();){
			Map<String,String> child = it.next();
			child.clear();
		}
	}

	public String getData() {
		return data;
	}

	public List<Map<String, String>> getChildren() {
		return children;
	}
}
