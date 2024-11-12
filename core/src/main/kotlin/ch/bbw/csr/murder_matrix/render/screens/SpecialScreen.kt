package ch.bbw.csr.murder_matrix.render.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch

abstract class SpecialScreen {
    protected val font: BitmapFont = BitmapFont()
    protected val layout: GlyphLayout = GlyphLayout()

    protected abstract val mainText: String
    protected open val secondaryText: String = "Press SPACE to exit"

    init {
        font.color = Color.WHITE
        font.getData().setScale(2f)
    }

    fun render(batch: SpriteBatch) {
        val centerX = Gdx.graphics.width / 2f
        val centerY = Gdx.graphics.height / 2f

        batch.begin()

        // Render main text
        layout.setText(font, mainText)
        font.draw(
            batch,
            mainText,
            centerX - layout.width / 2,
            centerY + layout.height / 2
        )

        // Render secondary text
        layout.setText(font, secondaryText)
        font.draw(
            batch,
            secondaryText,
            centerX - layout.width / 2,
            centerY - layout.height * 2
        )

        batch.end()

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            Gdx.app.exit()
        }
    }

    fun dispose() {
        font.dispose()
    }
}
