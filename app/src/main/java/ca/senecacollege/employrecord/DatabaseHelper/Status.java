package ca.senecacollege.employrecord.DatabaseHelper;

public class Status{
        private int status_id;
        private String status_name;

        public Status(){}

        public Status(String _status_name){
            this.status_name = _status_name;
        }

        public void setStatusId(int _status_id){
            this.status_id = _status_id;
        }

        public int getStatusId(){
            return this.status_id;
        }

        public void setStatusName(String _status_name){
            this.status_name = _status_name;
        }

        public String getSttusName(){
            return this.status_name;
        }
}

