package com.example.netflop.data.models;

public class DateModel {
        private String maximum;
        private String minimum;

        public DateModel(String maximum, String minimum) {
            this.maximum = maximum;
            this.minimum = minimum;
        }



        public String getMaximum() { return maximum; }
        public void setMaximum(String value) { this.maximum = value; }

        public String getMinimum() { return minimum; }
        public void setMinimum(String value) { this.minimum = value; }
}
