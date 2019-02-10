package com.sillygames.sJump.screens;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.esotericsoftware.kryonet.Client;
import com.sillygames.sJump.gameCore;
import com.sillygames.sJump.controls.InputController;
import com.sillygames.sJump.helpers.MyButton;
import com.sillygames.sJump.screens.settings.Constants;

public class ClientDiscoveryScreen extends AbstractScreen {

    private BitmapFont font;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private ArrayList<MyButton> buttons;
    private MyButton currentButton;
    private MyButton backButton;
    private MyButton refreshButton;
    private MyButton manualIpButton;
    private ArrayList<MyButton> ipAddresses;
    private boolean markForDispose;
    private Client client;
    private boolean pressedButton;
    private Texture background;
    
    public ClientDiscoveryScreen(gameCore game) {
        super(game);
        background = new Texture("bg.png");
        show();
    }
    
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background, viewport.getLeftGutterWidth(),0);
        renderButtons(delta);
        batch.end();
        processInput();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.update();
    }

    @Override
    public void show() {
        client = new Client();
        client.start();
        font = game.getFont(120);
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(1280, 720, camera);
        camera.setToOrtho(false, 1280, 720);
        buttons = new ArrayList<MyButton>();
        ipAddresses = new ArrayList<MyButton>();
        markForDispose = false;
        addAllButtons();
        addIpButtons();
        pressedButton = false;
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        ipAddresses.clear();
        buttons.clear();
        batch.dispose();
        font.dispose();
        ipAddresses = null;
        buttons = null;
        font = null;
    }
    
    public void processInput() {
        currentButton = currentButton.process();
        if (InputController.instance.buttonA() || markForDispose){
            processButton();
            return;
        }
        if(Gdx.input.isTouched()) {
            processTouched();
        }
    }
    
    private void processButton() {
        if (currentButton == backButton) {
            game.setScreen(new MainMenuScreen(game));
        } else if (currentButton == refreshButton) {
            addIpButtons();
        } else if (currentButton == manualIpButton) {
            if (pressedButton) {
                return;
            }
            pressedButton = true;
            
            Gdx.input.getTextInput(new TextInputListener() {
                
                @Override
                public void input(String text) {
                    joinGame(text);
                }
                
                @Override
                public void canceled() {
                    pressedButton = false;
                }
            }, "Enter IP", "");
            
        } else {
            if (!pressedButton) {
                pressedButton = true;
                joinGame(currentButton.getText());
            }
        }
    }
    
    private void processTouched() {
        Vector2 touchVector = viewport.unproject(new Vector2(Gdx.input.getX(),
                Gdx.input.getY()));
        for (MyButton button: buttons) {
            if(button.isPressed(touchVector, font)) {
                currentButton = button;
                processButton();
                return;
            }
        }
        for (MyButton button: ipAddresses) {
            if(button.isPressed(touchVector, font)) {
                currentButton = button;
                processButton();
                return;
            }
        }
    }

    public MyButton addButton(String text, float x, float y) {
        MyButton button = new MyButton(text, x, y);
        buttons.add(button);
        return button;
    }
    
    private void renderButtons(float delta) {
        for (MyButton button : buttons) {
            button.render(batch, font, delta);
        }
        for (MyButton button : ipAddresses) {
            button.render(batch, font, delta);
        }
    }
    
    private void addAllButtons() {
        refreshButton = addButton("Refresh", 100, 700);
        backButton = addButton("Back", 900, 700);
        manualIpButton = addButton("Enter IP", 50, 580);
        currentButton = refreshButton;
        currentButton.setActive(true);
        refreshButton.setWest(backButton);
        manualIpButton.setNorth(backButton);
        manualIpButton.setNorth(refreshButton);
    }
    
    private void addIpButtons() {
        game.platformServices.shortToast("Searching for servers");
        manualIpButton.north = null;
        ipAddresses.clear();
        MyButton previousButton = refreshButton;
        float y = 450;
        List<InetAddress> tempAddresses = client.discoverHosts(Constants.DISCOVERY_UDP_PORT, 
                Constants.TIMEOUT);
        if (tempAddresses.size() == 0) {
            game.platformServices.toast("no servers found");
        }
        
        for (InetAddress address : tempAddresses) {
            MyButton button = new MyButton(address.getHostName(), 100, y);
            ipAddresses.add(button);
            previousButton.setSouth(button);
            previousButton = button;
            y -= 130;
        }
        manualIpButton.setPosition(100, y);
        manualIpButton.setNorth(previousButton);
        
    }
    
    private void joinGame (final String host) {
        // FIXME
//            LobbyScreen lobbyScreen = new LobbyScreen(game);
//            lobbyScreen.setHost(address);
//            lobbyScreen.setServer(false);
//            game.setScreen(lobbyScreen);
            GameScreen gameScreen = new GameScreen(game);
            final Preferences prefs = Gdx.app.getPreferences("profile");
            if (gameScreen.loadLevel("maps/retro-small.tmx", host,
                    prefs.getString("name"))) {
                game.setScreen(gameScreen);
            } else {
                gameScreen.dispose();
            }
        
    }

}
