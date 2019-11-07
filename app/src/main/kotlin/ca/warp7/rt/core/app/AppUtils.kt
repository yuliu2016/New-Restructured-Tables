package ca.warp7.rt.core.app

import ca.warp7.rt.api.SearchFlavour
import ca.warp7.rt.api.SearchResult
import ca.warp7.rt.api.get
import ca.warp7.rt.core.Restructured
import ca.warp7.rt.core.model.Contexts
import ca.warp7.rt.core.model.Metadata
import ca.warp7.rt.ext.scanner.ScannerFeature
import ca.warp7.rt.ext.views.ViewsFeature
import javafx.application.Platform
import javafx.fxml.FXMLLoader
import javafx.geometry.Pos
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.image.Image
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.stage.Screen
import javafx.stage.Stage
import javafx.stage.StageStyle
import javafx.stage.WindowEvent
import org.kordamp.ikonli.javafx.FontIcon
import java.io.IOException

internal val appFeatures = listOf(
        ScannerFeature(),
        ViewsFeature()//,
//        ,ScannerFeature()
        //ASTFeature(),
        //PredictorFeature()
)

internal var utilsController: AppController? = null

private val teamColor: Color = Color.valueOf("1e2e4a")

internal fun tabUIFromLink(wrapper: FeatureWrapper): HBox {
    val outer = HBox()
    val inner = HBox()
    inner.prefWidth = 20.0
    inner.alignment = Pos.CENTER_LEFT
    wrapper.icon.iconColor = teamColor
    wrapper.icon.iconSize -= 2
    inner.children.add(wrapper.icon)
    outer.alignment = Pos.CENTER_LEFT
    outer.spacing = 6.0
    outer.children.add(inner)
    val label = Label(wrapper.link.title)
    outer.children.add(label)
    return outer
}

@Suppress("unused")
fun setAppStatus(statusMessage: String) {
    if (utilsController != null) utilsController!!.statusMessageLabel.text = statusMessage
}

fun getAndSaveUserSettings() {
    val dialog = Dialog<SettingsData>()

    dialog.title = "Settings"
    dialog.initOwner(utilsController?.appStage)

    val vBox = VBox()
    vBox.spacing = 10.0
    vBox.alignment = Pos.TOP_LEFT
    vBox.style = "-fx-padding: 10"

    val userLabel = Label("App User (First Last)")

    val userField = TextField()
    userField.text = Contexts.metadata[Metadata.appUser, System.getProperty("user.name")]

    vBox.children.addAll(userLabel, userField)

    val dsLabel = Label("Data Source (e.g. Team0000)")
    val dsField = TextField()
    dsField.text = Contexts.metadata[Metadata.dataSource, "Team865"]

    vBox.children.addAll(dsLabel, dsField)

    val tbaLabel = Label("The Blue Alliance API Key")
    val tbaField = TextField()
    tbaField.text = Contexts.metadata[Metadata.tbaKey, ""]

    vBox.children.addAll(tbaLabel, tbaField)

    vBox.minWidth = 640.0

    dialog.dialogPane.content = vBox
    dialog.dialogPane.buttonTypes.addAll(ButtonType.OK, ButtonType.CANCEL)

    val window = dialog.dialogPane.scene.window
    window.addEventHandler(WindowEvent.WINDOW_SHOWN) {
        val screenBounds = Screen.getPrimary().visualBounds
        window.x = (screenBounds.width - window.width) / 2
        window.y = (screenBounds.height - window.height) / 2
    }

    val okButton = dialog.dialogPane.lookupButton(ButtonType.OK)
    okButton.isDisable = true
    userField.textProperty().addListener { _, _, newValue ->
        okButton.isDisable = newValue.isEmpty()
    }

    Platform.runLater {
        userField.requestFocus()
    }

    dialog.setResultConverter {
        if (it == ButtonType.OK) {
            SettingsData(userField.text, dsField.text, tbaField.text)
        } else null
    }

    val settings = dialog.showAndWait().orElse(null)

    settings?.apply {
        Contexts.apply {
            metadata[Metadata.appUser] = user
            metadata[Metadata.dataSource] = dataSource
            metadata[Metadata.tbaKey] = tbaKey
//            root.save()
        }
    }
}

fun showStage(resFile: String, windowTitle: String) {
    val stage = Stage()
    val loader = FXMLLoader()
    loader.location = Restructured::class.java.getResource(resFile)
    stage.title = windowTitle
    stage.icons.add(Image(Restructured::class.java.getResourceAsStream("/ca/warp7/rt/res/app-icon.png")))
    try {
        stage.scene = Scene(loader.load())
        val controller = loader.getController<Any>()
        if (controller is FeatureStage) {
            controller.setStage(stage)
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    stage.show()
}

private fun createResultBody(result: SearchResult): VBox {
    val bodyContainer = VBox().apply { spacing = 10.0 }

    result.status?.apply {
        bodyContainer.children.add(HBox().apply {
            spacing = 10.0
            alignment = Pos.CENTER_LEFT
            style = "-fx-padding: 0 0 0 20"

            when (flavour) {
                SearchFlavour.Red -> styleClass.add("red-status")
                SearchFlavour.Green -> styleClass.add("green-status")
                SearchFlavour.Yellow -> styleClass.add("yellow-status")
                else -> Unit
            }

            children.add(FontIcon().apply { iconLiteral = "fas-history:16:0c0" })
            children.add(Label(message))
        })

    }

    if (result.summary.isNotEmpty()) {
        val itemsContainer = VBox()
        itemsContainer.style = "-fx-padding: 0 0 0 20"
        result.summary.forEach { k, v ->
            itemsContainer.children.add(HBox().apply {
                spacing = 10.0
                children.addAll(Label("$k:").apply {
                    alignment = Pos.CENTER_LEFT
                    minWidth = 100.0
                }, Label(v).apply {
                    styleClass.add("summary-bold")
                })
            })
        }
        bodyContainer.children.add(itemsContainer)
    }

    if (result.actionItems.isNotEmpty()) {
        val actionItems = VBox()
        actionItems.spacing = 10.0
        result.actionItems.forEach {
            actionItems.children.add(HBox().apply {
                styleClass.add("clicker-label-box")
                children.add(Label(it.name))
            })
        }
        bodyContainer.children.add(actionItems)
    }

    if (result.actionButtons.isNotEmpty()) {
        val actionButtons = HBox().apply {
            alignment = Pos.CENTER_RIGHT
            spacing = 10.0
        }
        result.actionButtons.forEach {
            actionButtons.children.add(Button().apply {
                text = it.name
                graphic = FontIcon().apply { iconLiteral = "${it.iconCode}:${it.iconSize}:white" }
                when (it.flavour) {
                    SearchFlavour.Red -> styleClass.add("red-button")
                    SearchFlavour.Green -> styleClass.add("green-button")
                    else -> Unit
                }
            })
        }
        bodyContainer.children.add(actionButtons)
    }

    return bodyContainer
}

fun createResultUI(result: SearchResult): Parent {
    val container = VBox().apply {
        stylesheets.add("/ca/warp7/rt/res/search.css")
        styleClass.add("search-block")
        spacing = 10.0
        alignment = Pos.CENTER_LEFT
    }

    val titleContainer = VBox()

    val titleHBox = HBox()
    val growingTitle = HBox().also { HBox.setHgrow(it, Priority.ALWAYS) }

    val titleLabel = Label(result.title).apply {
        styleClass.add("block-title")
    }

    growingTitle.children.add(titleLabel)

    val expandButton = Button().apply {
        styleClass.add("expand-button")
        graphic = FontIcon().apply {
            iconLiteral = "fas-external-link-alt:16:white"
        }
        setOnMouseClicked {
            Stage().apply {
                title = result.title
                if (result.header.isNotEmpty()) title += " | ${result.header}"
                initStyle(StageStyle.UTILITY)
                initOwner(utilsController?.appStage)
                val cont = createResultBody(result)
                val wrapper = VBox()
                wrapper.styleClass.add("search-block-det")
                wrapper.children.add(cont)
                wrapper.minWidth = 500.0
                scene = Scene(wrapper)
                opacity = 0.95
                scene.fill = Color.TRANSPARENT
                scene.stylesheets.add("/ca/warp7/rt/res/search.css")
                sizeToScene()
                show()
            }
        }
    }

    titleHBox.children.addAll(growingTitle, expandButton)
    titleContainer.children.add(titleHBox)

    if (result.header.isNotEmpty()) {
        val header = Label(result.header).apply {
            styleClass.add("block-header-text")
            style = "-fx-text-fill: #0ff;"
        }
        titleContainer.children.add(header)
    }

    container.children.addAll(titleContainer, createResultBody(result))

    return container
}