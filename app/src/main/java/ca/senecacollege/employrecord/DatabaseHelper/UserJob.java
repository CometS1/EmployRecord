package ca.senecacollege.employrecord.DatabaseHelper;

public class UserJob{
        private int user_job_id ;
        private int user_id;
        private int job_id;

        public UserJob(){}

        public UserJob(int _user_job_id, int _user_id, int _job_id){
            this.user_job_id  = _user_job_id ;
            this.user_id = _user_id;
            this.job_id = _job_id;
        }

        public void setUser_job_id(int _profile_id){
            this.user_job_id  = _profile_id;
        }

        public int getUser_job_id() {
            return this.user_job_id;
        }

        public void setUserId(int _user_id){
            this.user_id = _user_id;
        }

        public int getUser_id() {
            return this.user_id;
        }

        public void set_job_id(int _job_id){
            this.job_id = _job_id;
        }

        public int getJob_id() {
            return this.job_id;
        }
}