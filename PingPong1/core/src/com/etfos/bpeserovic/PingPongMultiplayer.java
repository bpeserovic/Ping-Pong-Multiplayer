package com.etfos.bpeserovic;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
/*
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.app.Service;*/

public class PingPongMultiplayer extends ApplicationAdapter implements InputProcessor {

	private SpriteBatch batch;

	private float width = 0;
	private float height = 0;

	private int score1 = 0;
	private int score2 = 0;
	private String brojString1 = "P1: 0";
	private String brojString2 = "P2: 0";

	BitmapFont brojFont;



	//klasa palica
	public class Palica {

		private Texture playerImg;
		private Sprite player;

		private float playerx;
		private float playery;

	}
	//konstruktori palica
	private Palica palica1;
	private Palica palica2;


	//klasa loptica
	public class Loptica {

		private Texture ballImg;
		private Sprite ball;

		private float xPosition = 200;
		private float yPosition = 200;

		private float xVector = 5;
		private float yVector = 5;

	}

	//konstruktor loptice
	private Loptica loptica;


	//konstruktor fonta
	//public BitmapFont font12;

	//generator fonta
	public void initFont() {
		FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/OpenSans-Regular.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

		fontParameter.size = 50;
		fontParameter.color = Color.RED;
		brojFont = fontGenerator.generateFont(fontParameter);
		fontGenerator.dispose();
	}


	@Override
	public void create () {


		//int x = AndroidLauncher.getInstance().mPhoneProperties.getX;
		batch = new SpriteBatch();

		width = Gdx.graphics.getWidth() - 1;
		height = Gdx.graphics.getHeight() - 1;

		//brojFont = new BitmapFont();
		//rojFont.setColor(1, 1, 1, 1);

		initFont();

		//font12 = new BitmapFont();


		loptica = new Loptica();
		palica1 = new Palica();
		palica2 = new Palica();

		loptica.ballImg = new Texture("ball_green.png");
		palica1.playerImg = new Texture("player1.png");
		palica2.playerImg = new Texture("player1.png");

		loptica.ball = new Sprite(loptica.ballImg);

		palica1.player = new Sprite(palica1.playerImg);
		//sprite1.setPosition(width/2 - sprite1.getWidth()/2, 0);

		palica2.player = new Sprite(palica2.playerImg);
		//sprite2.setPosition(width/2 - sprite2.getWidth()/2, height + sprite2.getHeight());

		palica1.playerx = width/2 - palica1.player.getWidth()/2;
		palica1.playery = 100;

		palica2.playerx = width/2 - palica2.player.getWidth()/2;
		palica2.playery = height - (palica2.player.getHeight()) - 200;

		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render () {
//if guest radi ovo else radi ono
		//System.out.println("boris size = " + getResources().getDisplayMetrics().heightPixels
		//		+ " x " + getResources().getDisplayMetrics().widthPixels);
		//System.out.println("Boris " + height	+ "x" + width);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();	//pocetak crtanja


		//igrac 1
		batch.draw(palica1.player,
				palica1.playerx,
				palica1.playery,
				palica1.player.getX(),
				palica1.player.getY(),
				palica1.player.getWidth(),
				palica1.player.getHeight(),
				palica1.player.getScaleX(),
				palica1.player.getScaleY(),
				0);

		//igrac 2
		batch.draw(palica2.player,
				palica2.playerx,
				palica2.playery,
				palica2.player.getX(),
				palica2.player.getY(),
				palica2.player.getWidth(),
				palica2.player.getHeight(),
				palica2.player.getScaleX(),
				palica2.player.getScaleY(),
				0);

		//lopta
		batch.draw(loptica.ball,
				loptica.xPosition,
				loptica.yPosition,
				loptica.ball.getX(),
				loptica.ball.getY(),
				loptica.ball.getWidth(),
				loptica.ball.getHeight(),
				loptica.ball.getScaleX(),
				loptica.ball.getScaleY(),
				0);

		//score1
		brojFont.draw(batch, brojString1, width/8, height - 100);

		//score2
		brojFont.draw(batch, brojString2, width/2 + width/8, height - 100);

		batch.end();	//kraj crtanja


		//micanje loptice
		loptica.xPosition += loptica.xVector;
		loptica.yPosition += loptica.yVector;


		collisionDetection();

	}

	@Override
	public void dispose () {
		batch.dispose();
		loptica.ballImg.dispose();
		palica1.playerImg.dispose();
		palica2.playerImg.dispose();
		brojFont.dispose();

	}

	private void collisionDetection(){

		//zidovi
		if(loptica.xPosition >= width - loptica.ball.getWidth())
		{
			loptica.xVector = -loptica.xVector;
		}
		if(loptica.xPosition <= 0){loptica.xVector = -loptica.xVector;
		}

		//palica 1
		if((loptica.yPosition <= 101 + palica1.player.getHeight()))
		{
			if((loptica.xPosition + loptica.ball.getWidth()/2 >= palica1.playerx - 10)&&
				(loptica.xPosition + loptica.ball.getWidth()/2 <= palica1.playerx + palica1.player.getWidth() + 10 ))
			{
				loptica.yVector = -loptica.yVector;
			} else {
				//Debug kod, maknit poslije
				try {
					Thread.sleep(2000);//2s
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				loptica.yVector = -loptica.yVector;
				loptica.xPosition = 200;
				loptica.yPosition = 200;

				//temp code promijenit poslije
				if (score2 <= 9) {

					score2++;
					brojString2 = "P2: " + score2;
				}
				else {
					score2 = 0;
					brojString2 = "P2:" + score2;
				}

			}
		}

		//palica 2 gotnji zid *placeholder*
		if((loptica.yPosition + loptica.ball.getHeight()) >= (height - 200 - palica2.player.getY()))
		{
			loptica.yVector = -loptica.yVector;
		}
	}

	///// kontrole
	////TODO treba dodati kontrole pomoću žiroskopa i toga

	public Vector2 lastTouch = new Vector2();


	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		lastTouch.set(screenX, screenY);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		Vector2 newTouch = new Vector2(screenX, screenY);
		Vector2 delta = newTouch.cpy().sub(lastTouch);
		lastTouch = newTouch;


		if ((delta.x < 0) && (palica1.playerx >= 0)) {
			//player1.translateX(-10f);
			palica1.playerx -= 15;
		}
		else
		{
			palica1.playerx -= 0;
		}

		if ((delta.x > 0) && (palica1.playerx + palica1.player.getWidth() <= width )) {
			//player1.translateX(10f);
			palica1.playerx += 15;
		}
		else
		{
			palica1.playerx += 0;
		}
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
