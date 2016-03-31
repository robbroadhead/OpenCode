package com.sns.Util;

public class Global
{
        private Global() {
                version_A = "1.0";
        }

        static private Global instance_A;

        static public Global getInstance() {
                if (instance_A == null) {
                        synchronized (Global.class) {
                        if (instance_A == null)
                                instance_A = new Global();
                        }
                }
                return instance_A;
        }

        public String getVersion () {
                return version_A;
        }

        private String version_A;
}
