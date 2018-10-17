package com.ue.projectplum;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Intersector.MinimumTranslationVector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.ue.projectplum.assethandling.AssetManager;

public class BaseActor extends Group {
	public TextureRegion region;
	public Rectangle boundary;

	protected Polygon boundingPolygon;
	public float centerX;
	public float centerY;
	public Vector2 center;
	
	//REQUIRED FOR ANIMATIONS
	protected float elapsedTime;
	
	
	
	public Texture texture;
	public Animation<TextureRegion> animation;
	
	/**
	 * Creates a BaseActor with a texture at path
	 * @param path path to texture
	 * @deprecated because of the new TextureHandler class preloading Textures
	 */
	@Deprecated
	public BaseActor(String path) {
		super();
		region = new TextureRegion();
		boundary = new Rectangle();
		
		elapsedTime = 0;
		this.center = new Vector2();
		
		genTexture(path);
		this.setOriginX(this.getWidth() / 2);
		this.setOriginY(this.getHeight() / 2);
		
		this.setRectangleBoundary();
		
	}
	/**
	 * Creates a BaseActor with a given texture
	 * @param t the texture
	 */
	
	public BaseActor(Texture t) {
		super();
		//init new boundaries and regions and center
		region = new TextureRegion();
		boundary = new Rectangle();
		elapsedTime = 0;
		this.center = new Vector2();
		//set texture and origin, as well as boundary
		this.setTexture(t);
		this.setOriginX(this.getWidth() / 2);
		this.setOriginY(this.getHeight() / 2);

		this.setRectangleBoundary();
	


	}
	/**
	 * Creates a BaseActor without a texture
	 * Rendering a BaseActor a texture may cause issues
	 */
	
	public BaseActor(){
		super();
		//init region, boundary and center
		region = new TextureRegion();
		boundary = new Rectangle();
	
		elapsedTime = 0;
		this.center = new Vector2();
	
		//setup rectangle boundary, might not be necessary because the BaseActor lacks a texture
		this.setRectangleBoundary();

	}
	/**
	 * Sets the texture of the BaseActor
	 * @param t the new texture
	 */
	public void setTexture(Texture t) {
		//set the texture field
		texture = t;
		//get the dimensions of the texture
		int w = t.getWidth();
		int h = t.getHeight();
		//set the BaseActors dimensions to the dimensions of the texture
		setWidth(w);
		setHeight(h);
		//set the texture region to be the new texture
		region.setRegion(t);
		//update the origin
		this.setOriginX(this.getWidth() / 2);
		this.setOriginY(this.getHeight() / 2);

		

	}
	/**
	 * Loads a texture from a file, currently deprecated
	 * @param path the path to the texture
	 * @deprecated because of the new TextureHandler class preloading Textures
	 */
	@Deprecated
	public void genTexture(String path) {
	
		try {
			Texture t = new Texture(Gdx.files.internal(path));
			setTexture(t);
			

		} catch (Exception GdxRuntimeException) {
			System.out.println("Error: Could not find: " + path + " substituting...");
			Texture t = new Texture(Gdx.files.internal("assets/missingTex.png"));
			setTexture(t);
		}
	}
	/**
	 * Loads an animation from the texture at path
	 * @param path the path to the texture
	 * @param cols the number of columns in the sprite sheet
	 * @param rows the number of rows in the sprite sheet
	 * @deprecated AssetManager needs to be updated to handle this
	 */
	@Deprecated
	public void genAnimation(String path, int cols, int rows) {
		//load the sprite sheet
		Texture sheet = AssetManager.getTexture(path);
		//map the sprite sheet into rows and columns
		TextureRegion[][] map = TextureRegion.split(sheet, sheet.getWidth()/cols, sheet.getHeight()/rows);
		//create array of frames
		TextureRegion[] frames = new TextureRegion[cols * rows];
		//add frames to frame array in proper order
		int index = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				frames[index++] = map[i][j];
			}
		}
		//set the animation field to a new animation using the frames
		animation = new Animation<TextureRegion>(0.025f, frames);
	
	
		
		
		
		
	}
	/**
	 * Sets the animation of the BaseActor
	 * @param a the animation
	 */
	
	public void setAnimation(Animation<TextureRegion> a){
		this.animation = a;
	
	
	}
	/**
	 * Gets the bounding rectangle for the BaseActor
	 * @return the bounding rectangle
	 */
	public Rectangle getBoundingRectangle() {
		boundary.set(getX(), getY(), getWidth(), getHeight());
		return boundary;

	}
	
	/**
	 * Updates the bounding polygon to fit within the size of the
	 * BaseActor's texture
	 */

	public void setRectangleBoundary() {
		float w = getWidth();
		float h = getHeight();
		float[] vertices = { 0, 0, w, 0, w, h, 0, h };
		boundingPolygon = new Polygon(vertices);
		boundingPolygon.setOrigin(getOriginX(), getOriginY());
	}
	/**
	 * Gets the bounding polygon of the BaseActor
	 * @return Polygon the bounding polygon
	 */
	public Polygon getBoundingPolygon() {
		boundingPolygon.setPosition(getX(), getY());
		boundingPolygon.getRotation();
		return boundingPolygon;
	}
	
	/**
	 * Checks to see if this BaseActor is overlapping with other, and if resolve
	 * moves this BaseActor so they are no longer overlapping
	 * @param other the other BaseActor
	 * @param resolve whether or not to move the BaseActors away from each other
	 * @return whether or not the two BaseActors are overlapping
	 */

	public boolean overlaps(BaseActor other, boolean resolve) {
		//get the two bounding polygons
		Polygon poly1 = this.getBoundingPolygon();
		Polygon poly2 = other.getBoundingPolygon();
		//check to see if the boundingRectangles are overlapping
		if (!poly1.getBoundingRectangle().overlaps(poly2.getBoundingRectangle()))
			return false;
		//make a new mtv and check to see if the bounding polygons are overlapping
		MinimumTranslationVector mtv = new MinimumTranslationVector();
		boolean polyOverlap = Intersector.overlapConvexPolygons(poly1, poly2, mtv);
		//if they are, and resolve is true, move this BaseAcotr away using the mtv
		if (polyOverlap && resolve) {
			this.moveBy(mtv.normal.x * mtv.depth, mtv.normal.y * mtv.depth);
		}
		//check to see if the mtv was actually great enough to be significant
		float significant = 0.5f;
		return (polyOverlap && (mtv.depth > significant));

	}
	/**
	 * The update function, called every game loop
	 * @param dt The delta time
	 */
	public void act(float dt) {
		super.act(dt);
		//increase elapsed time
		elapsedTime += Gdx.graphics.getDeltaTime();
		//update center
		center.x = this.getX() + this.getWidth() / 2;
		center.y = this.getY() + this.getHeight() / 2;
		
	

	}
	/**
	 * Render the BaseActor
	 * @param batch the spriteBatch
	 * @param the parent BaseActor's alpha
	 */
	public void draw(Batch batch, float parentAlpha) {
		// region.setRegion(anim.getKeyFrame(elapsedTime));
		//get color of this BaseActor so it renders that color
		Color c = getColor();
		batch.setColor(c.r, c.g, c.b, c.a);
		if (isVisible()) {
			//it has an animation
			if (this.animation != null){
				//render it
				batch.draw(this.animation.getKeyFrame(elapsedTime, true),getX(),getY());
			
			} else {
				//render normal texture
				batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());

			}
		
		}
		
		super.draw(batch, parentAlpha);
	}
	/**
	 * Changes this BaseActor to match another BaseActor
	 * @param orig the BaseActor to copy from
	 */
	public void copy(BaseActor orig) {
		this.region = new TextureRegion(orig.region);
		if (orig.boundingPolygon != null) {
			this.boundingPolygon = new Polygon(orig.boundingPolygon.getVertices());
			this.boundingPolygon.setOrigin(orig.getOriginX(), orig.getOriginY());

		}
		this.setPosition(orig.getX(), orig.getY());
		this.setOriginX(orig.getOriginX());
		this.setOriginY(orig.getOriginY());
		this.setWidth(orig.getWidth());
		this.setHeight(orig.getHeight());
		this.setColor(orig.getColor());
		this.setVisible(orig.isVisible());

	}
	/**
	 * Copies this BaseActor and returns a clone
	 * @return the cloned BaseActor
	 */
	public BaseActor clone() {
		System.out.println("clone!");
		BaseActor newbie = new BaseActor(this.texture);
		newbie.copy(this);
		return newbie;
	}
	/**
	 * Calculates and returns the angle at which a point is from the center of this BaseActor
	 * @param x the x cord of the point
	 * @param y the y cord of the point
	 * @param speed the speed at which to rotate
	 * @param rotate whether to rotate the BaseActor or not
	 * @return the angle between the BaseActor and the point (x,y)
	 */
	public double pointAt(double x, double y, float speed, boolean rotate) {
		//get the difference in their cords
		double yDiff = y - this.center.y;
		double xDiff = x - this.center.x;
		//use atan2 to find the new angle, then convert it to degrees
		double newAngle = Math.toDegrees(Math.atan2(yDiff, xDiff));
		//rotate the BaseActor if necessary
		if (rotate) {
			this.addAction(Actions.rotateTo((float) newAngle, speed));
		}
		return newAngle;

	}
	/**
	 * Calculates and returns the distance to a point
	 * @param x the x cord
	 * @param y the y cord
	 * @return the distance from the BaseActor to the point (x,y)
	 */
	public double distanceTo(double x, double y) {

		return Math.hypot(Math.abs(this.center.x - x), Math.abs(this.center.y - y));
	}
	/**
	 * Moves the center of the BaseActor to the point (x,y)
	 * Actually moves the BaseActor
	 * @param x the x cord
	 * @param y the y cord
	 */
	public void setCenter(float x, float y) {
		this.setPosition(x - this.getWidth() / 2, y - this.getHeight() / 2);
	}
	
	

}
