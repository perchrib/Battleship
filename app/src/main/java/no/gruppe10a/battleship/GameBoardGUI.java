package no.gruppe10a.battleship;

import org.andengine.engine.options.EngineOptions;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.SimpleAsyncGameActivity;
import org.andengine.util.progress.IProgressListener;

/**
 * Created by Eirik on 15/04/15.
 */
public class GameBoardGUI extends SimpleAsyncGameActivity implements ButtonSprite.OnClickListener {

    final private int CAMERA_WIDTH = 480;
    final private int CAMERA_HEIGTH = 480;

    final private int GRID_WIDTH = 10;
    final private int GRID_HEIGTH = 10;

    private BuildableBitmapTextureAtlas atlas;
    private ITextureRegion region;


    @Override
    public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {

    }

    @Override
    public void onCreateResourcesAsync(IProgressListener pProgressListener) throws Exception {

    }

    @Override
    public Scene onCreateSceneAsync(IProgressListener pProgressListener) throws Exception {
        return null;
    }

    @Override
    public void onPopulateSceneAsync(Scene pScene, IProgressListener pProgressListener) throws Exception {

    }

    @Override
    public EngineOptions onCreateEngineOptions() {
        return null;
    }
}
