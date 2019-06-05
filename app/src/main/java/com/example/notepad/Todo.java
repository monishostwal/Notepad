package com.example.notepad;

public class Todo {
    Boolean check;
    String task;

    public Todo(String task,boolean b) {
        this.task = task;
        this.check=b;
    }

    final public Boolean getCheck() {
        return check;
    }

   final public void setCheck(Boolean check) {
        this.check = check;
    }

   final public String getTask() {
        return task;
    }

   final public void setTask(String task) {
        this.task = task;
    }
}
