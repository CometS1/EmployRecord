package ca.senecacollege.employrecord.DatabaseHelper;

public class Colour{

        private int colour_id;
        private String hexcode;
        private String name;

        public Colour(){}

        public Colour(int _colour_id, String _hexcode){
            this.colour_id = _colour_id;
            this.hexcode = _hexcode;
        }

        public void setColourId(int _colour_id){
            this.colour_id = _colour_id;
        }

        public int getColourId() {
            return this.colour_id;
        }

        public void setColourName (String _name){
            this.name = _name;
        }

        public String getName() {
            return this.name;
        }

        public void setHexcode(String _hexcode){
            this.hexcode = _hexcode;
        }

        public String getHexcode() {
            return  this.hexcode;
        }
}
