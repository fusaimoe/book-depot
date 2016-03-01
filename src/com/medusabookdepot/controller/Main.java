package com.medusabookdepot.controller;

import com.medusabookdepot.view.*;

public class Main {

	private final GUI firstFrame;

	public Main() {
		this.firstFrame = new GUI();
		this.firstFrame.launcher(new String[] {});
	}

	public static void main(String[] args) throws Exception {
		new Main();
	}

}
