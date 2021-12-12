package com.example.hotbuildingsinyourarea;

public class MyDialogFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNum = getArguments().getInt("num");
    ...
    }
}
