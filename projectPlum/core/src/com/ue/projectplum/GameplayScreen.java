package com.ue.projectplum;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ue.projectplum.assethandling.AssetManager;


public class GameplayScreen implements Screen {

	public Stage mainStage;
	public Stage uiStage;
	private BaseActor missingTexture;
	public Game game;
	private ShapeRenderer shapeRender;

	public static Vector2 mousePos = new Vector2();

	

	private boolean hasFocusedOnSelectedPlanet;

	private Camera camera;
	private Camera uiCamera;
	private Viewport viewport;
	private Viewport uiViewport;
	


	



	
	private Vector2 uiMousePos = new Vector2();

	public GameplayScreen(Game g) {
		game = g;
		create();
	}

	public void create() {
		AssetManager.preLoadAssets();
		camera = new OrthographicCamera();
		uiCamera = new OrthographicCamera();
		viewport = new ScreenViewport(camera);
		uiViewport = new ScalingViewport(Scaling.fill, projectPlum.viewWidth, projectPlum.viewHeight, uiCamera);

		mainStage = new Stage(new ScreenViewport(camera));
		uiStage = new Stage(uiViewport);
		
		
		missingTexture = new BaseActor(AssetManager.getTexture("missingTexture"));
		mainStage.addActor(missingTexture);
		// Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());

		shapeRender = new ShapeRenderer();
		

	}

	public void render(float dt) {

		mainStage.act(dt);
		uiStage.act();

	

	

		OrthographicCamera cam = (OrthographicCamera) mainStage.getCamera();
		Vector2 center = new Vector2();
		
		cam.update();

		
		
		Gdx.gl.glClearColor(0.9F, 0.9F, 1, 1);
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		uiCamera.update();
		// camera
		// shapeRender.begin(ShapeType.Line);
		// shapeRender.setColor(Color.RED);
		// shapeRender.circle(0, 0, 200);
		// shapeRender.rect(1, 0, PS.viewWidth-1, PS.viewHeight-220);
		// shapeRender.end();
		
		mainStage.getViewport().apply();
		mainStage.draw();
		uiStage.getViewport().apply();
		uiStage.draw();

		

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		mainStage.getViewport().update(width, height, false);
		uiStage.getViewport().update(width, height, false);
		viewport.update(width, height);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	
	

}
