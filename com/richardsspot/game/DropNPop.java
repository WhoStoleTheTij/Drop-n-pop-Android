package com.richardsspot.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer;

import imageActors.Score;
import screens.LoadingScreen;
import screens.MenuScreen;

public class DropNPop extends Game{
	Game game;
	IActivityRequestHandler handler;

	private static long SPLASH_MINIMUM_MILLIS = 3000L;

	public DropNPop(IActivityRequestHandler handler){
		super();
		this.handler = handler;
		this.handler.showAds(false);
	}
	
	@Override
	public void create () {
		Assets.getInstance().loadAssets();
		//change to the loading screen and load the assets

		setScreen(new LoadingScreen());
		game = this;



		final long splashStartTime = System.currentTimeMillis();

		new Thread(new Runnable(){

			@Override
			public void run() {

				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {

						long splashTimeElapsed = System.currentTimeMillis() - splashStartTime;
						if(splashTimeElapsed < DropNPop.SPLASH_MINIMUM_MILLIS){
							Timer.schedule(
								new Timer.Task(){
									@Override
									public void run(){

										DropNPop.this.setScreen(new MenuScreen(game, handler));
									}
								}, (float)(DropNPop.SPLASH_MINIMUM_MILLIS - splashTimeElapsed) / 1000f);
						}else{
							DropNPop.this.setScreen(new MenuScreen(game, handler));
						}
					}
				});
			}
		}).start();

	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
	}
}
