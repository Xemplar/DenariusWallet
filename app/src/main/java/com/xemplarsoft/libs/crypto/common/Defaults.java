package com.xemplarsoft.libs.crypto.common;

import java.math.RoundingMode;

/**A set of default values for commonly used call parameters.*/
public final class Defaults {

	public static final int DECIMAL_SCALE = 8;
	public static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

	public static final String[] NODE_VERSIONS = {"0.10.0", "0.10.1", "0.10.2", "0.10.3"};
	public static final String JSON_RPC_VERSION = "1.0";
}