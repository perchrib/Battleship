package no.gruppe10a.battleship;

/**
 * Created by per on 09/04/15.
 */
import android.app.AlertDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Toast;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.primitive.Line;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.adt.cache.concurrent.SynchronizedIntLRUCache;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;

public class TicTacToeActivity extends SimpleBaseGameActivity implements ButtonSprite.OnClickListener, IOnSceneTouchListener {

    final private int CAMERA_WIDTH = 480;
    final private int CAMERA_HEIGHT = 800;

    final private int GRID_WIDTH = 10;
    final private int GRID_HEIGHT = 10;

    final private float STROKE_WIDTH = 5;

    private BuildableBitmapTextureAtlas mBitmapTextureAtlas;
    private ITextureRegion mBlankTextureRegion;
    private ITextureRegion mXTextureRegion;
    private ITextureRegion mOTextureRegion;
    private ITextureRegion mTouched;

    private Font mFont;
    private Text text;
    private BitmapTextureAtlas fontTextureAtlas;

    private BitmapTextureAtlas mFontTexture;

    //private TextureRegion myTextureRegion;
    private BitmapTextureAtlas mBitMapAtlas;

    private ButtonSprite[][] gridSprite = new ButtonSprite[GRID_WIDTH][GRID_HEIGHT];
    private ButtonSprite[][] gridTouchSprite = new ButtonSprite[GRID_WIDTH][GRID_HEIGHT];

    private Model board = new Model();
    private Piece currentPlayer = board.getCurrentPlayer();
    private TextureRegion mini;

    @Override
    protected void onCreate(Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
    }

    @Override
    protected void onCreateResources() {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");


        this.mBitmapTextureAtlas = new BuildableBitmapTextureAtlas(this.getTextureManager(),64, 64, TextureOptions.BILINEAR); //TextureOptions.BILINEAR);

        this.mBlankTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "mm.png");
        //this.mXTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "xicon.png");
        //this.mOTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "oicon.png");

        /*this.mBitMapAtlas = new BitmapTextureAtlas(this.getTextureManager(), 32, 32, TextureOptions.BILINEAR);
        this.myTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitMapAtlas, this, "mark.png", 0, 0);
        this.mBitMapAtlas.load();*/

        // Fonts

        //this.mFontTexture = new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

        //this.mFont = new Font(this.mFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 48, true, Color.BLACK);

        //this.mEngine.getTextureManager().loadTexture(this.mFontTexture);
        //this.getFontManager().loadFont(this.mFont);
        //this.mFont = FontFactory.create(this.getFontManager(),this.getTextureManager(),256,256, Typeface.create(Typeface.DEFAULT,Typeface.BOLD_ITALIC),24,Color.WHITE );

        //fontTextureAtlas = new BitmapTextureAtlas(1024, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

        //this.mFont=FontFactory.create(this.getFontManager(),this.getTextureManager(),256,256,TextureOptions.BILINEAR,Typeface.DEFAULT,12,true,Color.WHITE);


         //this.mFont.load();


        //this.getFontManager().loadFonts(this.mFont);
        //this.getFontManager().loadFont(this.mFont);
        //getEngine().getFontManager().loadFont(this.mFont);



        this.mTouched = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "mark.png");
        this.mini = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "mini.png");

        try {
            this.mBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
            this.mBitmapTextureAtlas.load();
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            Debug.e(e);
        }
    }

    @Override
    protected Scene onCreateScene() {
        final Scene scene = new Scene();
        final Scene status = new Scene();
        final VertexBufferObjectManager vertexBufferObjectManager = this.getVertexBufferObjectManager();


        final int w = 480;
        final int y = 240;

        //final TiledSprite labelSprite = new TiledSprite(480, 800-480, mResourceManager.mStateTextureRegion, vertexBufferObjectManager);

        //labelSprite.setCurrentTileIndex(1);
        //scene.attachChild(labelSprite);

        float lineX[] = new float[GRID_WIDTH];
        float lineY[] = new float[GRID_HEIGHT];

        float mlineX[] = new float[GRID_WIDTH];
        float mlineY[] = new float[GRID_HEIGHT];

        float touchX[] = new float[GRID_WIDTH];
        float touchY[] = new float[GRID_HEIGHT];

        float mtouchX[] = new float[GRID_WIDTH];
        float mtouchY[] = new float[GRID_HEIGHT];

        float midTouchX = CAMERA_WIDTH / GRID_WIDTH / 2;
        float midTouchY = CAMERA_WIDTH / GRID_HEIGHT / 2;

        float halfTouchX = mBlankTextureRegion.getWidth() / 2;
        float halfTouchY = mBlankTextureRegion.getHeight() / 2;

        System.out.println("TEXTURE WIDTH: " + mBlankTextureRegion.getWidth());
        System.out.println("TEXTURE HEIGHT " + mBlankTextureRegion.getHeight());
        float paddingX = midTouchX - halfTouchX;
        float paddingY = midTouchY - halfTouchY;



        for (int i = 0; i < GRID_WIDTH; i++) {
            System.out.println("I: " + i);
            lineX[i] = CAMERA_WIDTH / GRID_WIDTH * i;
            mlineX[i] = (CAMERA_WIDTH / GRID_WIDTH * i / 2);
            //lineX[i] = w / GRID_WIDTH * i;
            touchX[i] = lineX[i] + paddingX;

        }



        for (int i = 0; i < GRID_HEIGHT; i++) {
            lineY[i] = CAMERA_WIDTH / GRID_HEIGHT * i;
            mlineY[i] = 500 + (CAMERA_WIDTH / GRID_WIDTH * i / 2);
            //lineY[i] = y / GRID_WIDTH * i;
            touchY[i] = lineY[i] + paddingY;
        }

        //scene.setBackground(new Background(0.85f, 0.85f, 0.85f));
        scene.setBackground(new Background(Color.CYAN));
        // draw the grid lines
        //final Rectangle r = new Rectangle(0,0,480, 720-480, vertexBufferObjectManager);
        scene.setPosition(0,0);
        //r.setColor(Color.RED);
        //scene.attachChild(r);

        //Vertical
        for (int i = 0; i < GRID_WIDTH; i++) {
            final Line line = new Line(lineX[i], 0, lineX[i], CAMERA_WIDTH, STROKE_WIDTH, vertexBufferObjectManager);
            line.setColor(0.15f, 0.15f, 0.15f);
            scene.attachChild(line);

        }

        //Horizontal
        for (int i = 0; i < GRID_HEIGHT; i++) {
            final Line line = new Line(0, lineY[i], CAMERA_WIDTH, lineY[i] , STROKE_WIDTH, vertexBufferObjectManager);
            line.setColor(0.15f, 0.15f, 0.15f);
            scene.attachChild(line);
            if (i == GRID_HEIGHT-1){
                final Line l = new Line(0, lineY[i] + 48, CAMERA_WIDTH, lineY[i] + 48, STROKE_WIDTH, vertexBufferObjectManager);
                l.setColor(0.15f, 0.15f, 0.15f);
                scene.attachChild(l);
            }
        }


        // button sprites
        for (int i = 0; i < GRID_WIDTH; i++) {
            for (int j = 0; j < GRID_HEIGHT; j++) {
                //final ButtonSprite button = new ButtonSprite(touchX[i], touchY[j], this.mBlankTextureRegion, this.mTouched, this.mOTextureRegion,this.mXTextureRegion, this.getVertexBufferObjectManager(), this);
                final ButtonSprite button = new ButtonSprite(touchX[i], touchY[j], this.mBlankTextureRegion,this.mTouched, this.getVertexBufferObjectManager(), this);
                final ButtonSprite button1 = new ButtonSprite(mlineX[i], mlineY[j], this.mBlankTextureRegion,this.mini, this.getVertexBufferObjectManager(), this);
                /*{
                    @Override
                    public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                        System.out.println(pTouchAreaLocalX);
                        clickTile(this);
                        return true;
                    // here you can use the code
                }};*/

                scene.registerTouchArea(button);



                scene.attachChild(button);

                if (j == 0 && i == 0)
                    button1.setCurrentTileIndex(1);
                scene.attachChild(button1);






                //scene.setOnSceneTouchListener(this);
                gridSprite[i][j] = button;
                gridTouchSprite[i][j] = button;
            }
        }

        /*Sprite mySprite = new Sprite(800-480, 480, this.myTextureRegion, this.getVertexBufferObjectManager());
        mySprite.setPosition(0,480);
        mySprite.setColor(Color.RED);
        scene.attachChild(mySprite);
        */


        //scene.setChildScene(status);
        //Rectangle statusLabel = new Rectangle(0,480,480,800-480,this.getVertexBufferObjectManager());
        //statusLabel.setColor(Color.PINK);
        //scene.attachChild(statusLabel);
        //status.setOnSceneTouchListener(this);

        //Vertical
        for (int i = 0; i < GRID_WIDTH; i++) {
            final Line line = new Line(mlineX[i], 500 , mlineX[i], 740 , STROKE_WIDTH, vertexBufferObjectManager);
            line.setColor(0.15f, 0.15f, 0.15f);
            scene.attachChild(line);
            if (i == GRID_HEIGHT-1){
                final Line l = new Line(mlineX[i]+24, 500 , mlineX[i]+24, 740 , STROKE_WIDTH, vertexBufferObjectManager);
                l.setColor(0.15f, 0.15f, 0.15f);
                scene.attachChild(l);
            }


        }

        //Horizontal
        for (int i = 0; i < GRID_HEIGHT; i++) {
            final Line line = new Line(0, mlineY[i]  ,240, mlineY[i] , STROKE_WIDTH, vertexBufferObjectManager);
            line.setColor(0.15f, 0.15f, 0.15f);
            scene.attachChild(line);
            if (i == GRID_HEIGHT-1){
                final Line l = new Line(0, mlineY[i] + 24, 240, mlineY[i] + 24, STROKE_WIDTH, vertexBufferObjectManager);
                l.setColor(0.15f, 0.15f, 0.15f);
                scene.attachChild(l);
            }
        }


        //text = new Text(100, 200, this.mFont, "Also left aligned!", this.getVertexBufferObjectManager());
        //scene.attachChild(text);
        //text = new Text(20, 420, this.get, "Score: 0123456789", new TextOptions(HorizontalAlign.LEFT), this.getVertexBufferObjectManager());
       // final Text textNormal = new Text(100, 100, this.mFont, "Just some normal Text.");




        scene.setTouchAreaBindingOnActionDownEnabled(true);


        return scene;
    }

    @Override
    public EngineOptions onCreateEngineOptions() {
        final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

        EngineOptions options = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED,
                new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
        return options;
    }


    @Override
    public void onClick(final ButtonSprite pButtonSprite, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // determine which button was pressed
                //clickTile(pButtonSprite);
                float x = pButtonSprite.getX();
                float y = pButtonSprite.getY();
                System.out.println("X: " + x +" Y: " + y);

                int gridX = (int)Math.floor(x / CAMERA_WIDTH * GRID_WIDTH);
                int gridY = (int)Math.floor(y / CAMERA_WIDTH* GRID_HEIGHT);

                System.out.println("GRIDX: " + gridX + " GRIDY: "+ gridY);

                //MarkPoint m = new MarkPoint(gridX,gridY);
                //m.setMark(gridTouchSprite);
                AlertDialog.Builder ADBuilder = new AlertDialog.Builder(TicTacToeActivity.this);
                ADBuilder.setMessage("HALLA FITTE!!").show();

                //if (gridSprite[gridX][gridY] == pButtonSprite){



                    pButtonSprite.setCurrentTileIndex(1);


                }
                        //&& currentPlayer == board.getCurrentPlayer()) {
                    // update the data model
                   // board.setValue(gridX, gridY, currentPlayer);

                    // disable the button
                    //pButtonSprite.setEnabled(false);




                  //  if (currentPlayer == Piece.X) {
                        // change the sprite to x
                        //pButtonSprite.setCurrentTileIndex(3);
                        //pButtonSprite.setCurrentTileIndex(3);

                  //  } else {
                        // change the sprite to o
                        //pButtonSprite.setCurrentTileIndex(3);
                        //pButtonSprite.setCurrentTileIndex(2);
                  //  }



                  //  currentPlayer = board.getCurrentPlayer();
                //}
            }
        );
    }

    private void reset() {
        for (int i = 0; i < GRID_WIDTH; i++) {
            for (int j = 0; j < GRID_HEIGHT; j++) {
                gridSprite[i][j].setEnabled(true);
                gridSprite[i][j].setCurrentTileIndex(0);
            }
        }
    }

    /*public void clickTile(float x, float y){
        float x = pButtonSprite.getX();
        float y = pButtonSprite.getY();
        System.out.println("X: " + x +" Y: " + y);

        int gridX = (int)Math.floor(x / CAMERA_WIDTH * GRID_WIDTH);
        int gridY = (int)Math.floor(y / CAMERA_WIDTH* GRID_HEIGHT);

        System.out.println("GRIDX: " + gridX + " GRIDY: "+ gridY);

        MarkPoint m = new MarkPoint(gridX,gridY);
        m.setMark(gridSprite);
    }*/


    @Override
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
        System.out.println("GRIDX: " + pSceneTouchEvent.getX() + " GRIDY: "+ pSceneTouchEvent.getY());

        //clickTile(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
        return true;
    }
}
