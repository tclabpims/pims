package com.smart.webapp.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.smart.drools.Diff;
import com.smart.drools.Ratio;
import com.smart.model.rule.Item;
import com.smart.model.rule.Rule;
import com.smart.service.rule.RuleManager;


public class AuditUtil {

	private static Map<String, Diff> mDiff = null;
	private static Map<String, Diff> bDiff = null;
	private static Map<String, Ratio> ratio = null;

	public static Map<String, Diff> getMDiff(RuleManager ruleManager) {
		if (mDiff == null) {
			mDiff = dealDiff(ruleManager.getDiffRule(1, "(0,1)"));
		}
		return mDiff;
	}

	public static Map<String, Diff> getBDiff(RuleManager ruleManager) {
		if (bDiff == null) {
			bDiff = dealDiff(ruleManager.getDiffRule(1, "(0,2)"));
		}
		return bDiff;
	}

	public static Map<String, Ratio> getDealRatio(RuleManager ruleManager) {
		if (ratio == null) {
			ratio = dealRatio(ruleManager.getRuleByType(2));
		}
		return ratio;
	}

	private static Map<String, Diff> dealDiff(List<Rule> list) {

		Map<String, Diff> diff = new HashMap<String, Diff>();
		for (Rule r : list) {
			Set<Item> items = r.getItems();
			for (Item i : items) {
				String ref = i.getValue();
				Diff d = new Diff();
				d.setTestid(i.getIndexId());
				d.setAlgorithm(r.getAlgorithm());
				for (String s : ref.split("\\|\\|")) {
					if (s.contains("<=")) {
						d.setReflo(Float.parseFloat(s.replace("<=", "")));
					} else if (s.contains("<")) {
						d.setReflo(Float.parseFloat(s.replace("<", "")));
					}
					if (s.contains(">=")) {
						d.setRefhi(Float.parseFloat(s.replace(">=", "")));
					} else if (s.contains(">")) {
						d.setRefhi(Float.parseFloat(s.replace(">", "")));
					}
				}
				diff.put(i.getIndexId(), d);
			}
		}
		return diff;
	}

	private static Map<String, Ratio> dealRatio(List<Rule> list) {

		Map<String, Ratio> ratio = new HashMap<String, Ratio>();
		for (Rule r : list) {
			List<Item> items = new ArrayList<Item>(r.getItems());
			Ratio ra = new Ratio();
			String ref = "";
			if(items.size()==0)
				continue;
			if (items.get(0).getValue().equals("=1")) {
				ra.setDenominatorId(items.get(0).getIndexId());
				ra.setNumeratorId(items.get(1).getIndexId());
				ref = items.get(1).getValue();
			} else {
				ra.setDenominatorId(items.get(1).getIndexId());
				ra.setNumeratorId(items.get(0).getIndexId());
				ref = items.get(0).getValue();
			}
			if (ref.contains("||")) {
				for (String s : ref.split("\\|\\|")) {
					if (s.contains("<=")) {
						ra.setReflo(Float.parseFloat(s.replace("<=", "")));
					} else if (s.contains("<")) {
						ra.setReflo(Float.parseFloat(s.replace("<", "")));
					}
					if (s.contains(">=")) {
						ra.setRefhi(Float.parseFloat(s.replace(">=", "")));
					} else if (s.contains(">")) {
						ra.setRefhi(Float.parseFloat(s.replace(">", "")));
					}
				}
			} else {
				if (ref.contains("<=")) {
					ra.setReflo(Float.parseFloat(ref.replace("<=", "")));
				} else if (ref.contains("<")) {
					ra.setReflo(Float.parseFloat(ref.replace("<", "")));
				} else if (ref.contains(">=")) {
					ra.setRefhi(Float.parseFloat(ref.replace(">=", "")));
				} else if (ref.contains(">")) {
					ra.setRefhi(Float.parseFloat(ref.replace(">", "")));
				} else {
					ra.setRefhi(Float.parseFloat(ref.replace("=", "")));
					ra.setReflo(Float.parseFloat(ref.replace("=", "")));
				}

			}
			ratio.put(ra.getDenominatorId(), ra);
		}
		return ratio;
	}
}
