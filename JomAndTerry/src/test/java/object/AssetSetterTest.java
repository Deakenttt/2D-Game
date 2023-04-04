package object;

import main.GamePanel;
import org.junit.Test;

import static org.junit.Assert.*;

public class AssetSetterTest {

    GamePanel gp = new GamePanel();
    AssetSetter assetSetter = new AssetSetter(gp);
    @Test
    public void testIsTileAvailableAndCollision(){
        int col = assetSetter.getRandomNumber(1, 15);
        int row = assetSetter.getRandomNumber(1, 15);
        int tileNum = gp.tileManager.mapTileNum[col][row];
        boolean result = assetSetter.isTileAvailable(col, row);
        // there isn't a floor, can't put anything
        if(tileNum != 0) {
            assertFalse(result);
        }
        // there is a floor
        else{
            // check is the pos exist a player
            col = gp.player.x / gp.tileSize;
            row = gp.player.y / gp.tileSize;
            result = assetSetter.isTileAvailable(col, row);
            assertFalse(result);

            // check is the pos exist an object
            gp.obj[10] = new Cheese(gp);
            gp.obj[10].solidArea.x = 7 * gp.tileSize;
            gp.obj[10].solidArea.x = 3 * gp.tileSize;
            result = assetSetter.isTileAvailable(7, 3);
            assertFalse(result);
        }
    }

    @Test
    public void testObject_setter(){
        String[] nameList = {"cheese", "cheese", "cheese", "cheese",
                "cheese", "cheese", null, "trap", "trap", "hole"};
        assetSetter.setObject();
        int i = assetSetter.getRandomNumber(0,9);
        // randomly pick one object to test
        String objectName = gp.obj[i].name;
        // check if the object generate from setObject method are the same with the original
        assertEquals(objectName, nameList[i]);
    }
    @Test
    public void testSteakUpdate(){
        assetSetter.steakCounter = 0;
        gp.obj[6] = null;
        assetSetter.steakUpdate();
        assertEquals(gp.obj[6], new Steak(gp));
        gp.obj[6] = null;
        assetSetter.steakUpdate();
        assertNull(gp.obj[6]);
    }

}
