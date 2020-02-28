package com.example.qainfomate.Models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class Session {

    public static class LiveSession
    {
        public static User user;
    }

    public static class Notifications extends ViewModel
    {
        private MutableLiveData<List<Message>> msgs = new MutableLiveData<>();
        public MutableLiveData<List<Message>> getData(){
            return msgs;
        }
    }
}
