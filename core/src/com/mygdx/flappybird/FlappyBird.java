package com.mygdx.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture topTube;
	Texture bottomTube;
	Texture[] birds;

	int flapState = 0;
	float birdY = 0;
	float velocity = 0;
	float gravity = 2;

	float tubeGap = 400;
	float maxTubeOffset;
	Random gapGenerator;
	float tubeVelocity = 4;
	int numTubes = 4;
    float[] tubeX = new float[numTubes];
    float[] tubeOffset = new float[numTubes];
	float distanceBetweenTubes;

	int gameState = 0;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");

		birds = new Texture[2];
		birds[0] = new Texture("bird.png");
		birds[1] = new Texture("bird2.png");
		birdY = Gdx.graphics.getHeight() / 2 - birds[0].getHeight() / 2;

		topTube = new Texture("toptube.png");
		bottomTube = new Texture("bottomtube.png");
		maxTubeOffset = Gdx.graphics.getHeight() / 2 - tubeGap / 2 - 100;
		gapGenerator = new Random();
        distanceBetweenTubes = Gdx.graphics.getWidth() / 2;

        for (int i = 0; i < numTubes; i++) {
            tubeOffset[i] = (gapGenerator.nextFloat() - 0.5f) * maxTubeOffset;
            tubeX[i] = Gdx.graphics.getWidth() / 2 - topTube.getWidth() / 2 + i * distanceBetweenTubes;
        }
	}

	@Override
	public void render () {

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (gameState != 0) {

            if (Gdx.input.justTouched()) {
                velocity = -30;

            }

            for (int i = 0; i < numTubes; i++) {
                if (tubeX[i] < -topTube.getWidth()) {
                    tubeX[i] += numTubes * distanceBetweenTubes;
                }

                tubeX[i] -= tubeVelocity;

                batch.draw(topTube,
                    tubeX[i],
                    Gdx.graphics.getHeight() / 2 + tubeGap / 2 + tubeOffset[i]);
                batch.draw(bottomTube,
                    tubeX[i],
                    Gdx.graphics.getHeight() / 2 - tubeGap / 2 - bottomTube.getHeight() + tubeOffset[i]);
            }



            if (birdY > 0 || velocity < 0) {
                velocity += gravity;
                birdY -= velocity;
            }


        } else {
            if (Gdx.input.justTouched()) {
                gameState = 1;
            }
        }

        if (flapState == 0) {
            flapState = 1;
        } else {
            flapState = 0;
        }

        batch.draw(birds[flapState],
            Gdx.graphics.getWidth() / 2 - birds[flapState].getWidth() / 2,
            birdY);
        batch.end();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
	}
}
