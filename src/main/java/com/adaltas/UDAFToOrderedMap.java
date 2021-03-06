package com.adaltas;

import java.util.TreeMap;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.parse.SemanticException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFEvaluator;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfo;

/**
 * Convert two aggregated columns into a sorted key-value map.
 * 
 * The key must be a primitive type (int, boolean, float, string, ...) and the 
 * value may be a primitive or a complex type (structs, maps, arrays).
 * 
 * https://cwiki.apache.org/Hive/genericudafcasestudy.html
 */
@Description(name = "to_ordered_map", value = "_FUNC_(key, value) - Convert two aggregated columns into an ordered key-value map")
public class UDAFToOrderedMap extends UDAFToMap {

	public GenericUDAFEvaluator getEvaluator(TypeInfo[] parameters)
			throws SemanticException {

		super.getEvaluator(parameters);
		return new UDAFToOrderedMapEvaluator();
	}

	public static class UDAFToOrderedMapEvaluator extends UDAFToMapEvaluator {

		public void reset(AggregationBuffer agg) throws HiveException {
			((MkMapAggregationBuffer) agg).container = new TreeMap<Object,Object>();
		}
		
	}

}
