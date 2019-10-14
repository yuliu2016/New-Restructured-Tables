package ca.warp7.rt.core.app

import ca.warp7.rt.api.*
import javafx.application.Platform
import javafx.event.EventHandler
import javafx.scene.Parent
import javafx.scene.control.ScrollPane
import javafx.scene.control.TextField
import javafx.scene.input.ScrollEvent
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox


class SearchController {
    lateinit var searchField: TextField
    lateinit var scrollPane: ScrollPane
    lateinit var vBox: VBox
    lateinit var searchResults: VBox

    private val defRes: List<Parent> = listOf(
            createResultUI(SearchResult(
                    title = "Active Context",
                    header = "ONT District Humber College Event 2019",

                    summary = mapOf(
                            "Year" to "2019",
                            "Event" to "onto3",
                            "Data Source" to "Team 865",
                            "App User" to "Yu Liu",
                            "Version" to "6"
                    ),
                    actionButtons = listOf(
                            SearchActionButton(
                                    name = "Update Version",
                                    provider = { null },
                                    flavour = SearchFlavour.Normal,
                                    iconCode = "fas-plus",
                                    iconSize = 16),
                            SearchActionButton(
                                    name = "Revert",
                                    provider = { null },
                                    flavour = SearchFlavour.Normal,
                                    iconCode = "fas-undo",
                                    iconSize = 16),
                            SearchActionButton(
                                    name = "Rename",
                                    provider = { null },
                                    flavour = SearchFlavour.Normal,
                                    iconCode = "fas-font",
                                    iconSize = 16),
                            SearchActionButton(
                                    name = "Delete",
                                    provider = { null },
                                    flavour = SearchFlavour.Red,
                                    iconCode = "fas-trash",
                                    iconSize = 16)
                    )
            )),
            createResultUI(SearchResult(
                    title = "Recent Contexts",
                    actionItems = listOf(
                            SearchActionItem(name = "2019 Overall, v4") { null },
                            SearchActionItem(name = "ONT District Windsor Essex Great Lakes Event 2019, v1") { null }
                    )
            )),
            createResultUI(SearchResult(
                    title = "Tables",
                    actionItems = listOf(
                            SearchActionItem(name = "Team") { null },
                            SearchActionItem(name = "Match") { null },
                            SearchActionItem(name = "Entry") { null }
                    )
            )),
            createResultUI(SearchResult(
                    title = "The Blue Alliance Import",
                    header = "event/2019onto3",
                    summary = mapOf("Last Updated" to "9:00 AM"),
                    status = SearchStatus("All data up to date", SearchFlavour.Green),
                    actionButtons = listOf(
                            SearchActionButton(
                                    name = "Source",
                                    provider = { null },
                                    flavour = SearchFlavour.Normal,
                                    iconCode = "fas-plug",
                                    iconSize = 16),
                            SearchActionButton(
                                    name = "Fetch",
                                    provider = { null },
                                    flavour = SearchFlavour.Normal,
                                    iconCode = "fas-download",
                                    iconSize = 16),
                            SearchActionButton(
                                    name = "Merge",
                                    provider = { null },
                                    flavour = SearchFlavour.Green,
                                    iconCode = "fas-code-branch",
                                    iconSize = 16)
                    )
            )),
            createResultUI(SearchResult(
                    title = "External Export",
                    header = "E:\\warp7\\external\\Team865-2019-onto3",
                    summary = mapOf("Last Updated" to "8:20 AM"),
                    status = SearchStatus("All data up to date", SearchFlavour.Green),
                    actionButtons = listOf(
                            SearchActionButton(
                                    name = "Target",
                                    provider = { null },
                                    flavour = SearchFlavour.Normal,
                                    iconCode = "fas-bullseye",
                                    iconSize = 16),
                            SearchActionButton(
                                    name = "Upload",
                                    provider = { null },
                                    flavour = SearchFlavour.Green,
                                    iconCode = "fas-upload",
                                    iconSize = 16)
                    )
            )),
            createResultUI(SearchResult(
                    title = "External Import",
                    header = "E:\\warp7\\external\\Team865-2019-onto33",
                    summary = mapOf("Last Updated" to "8:22 AM"),
                    status = SearchStatus("New Data Available", SearchFlavour.Yellow),
                    actionButtons = listOf(
                            SearchActionButton(
                                    name = "Source",
                                    provider = { null },
                                    flavour = SearchFlavour.Normal,
                                    iconCode = "fas-plug",
                                    iconSize = 16),
                            SearchActionButton(
                                    name = "Fetch",
                                    provider = { null },
                                    flavour = SearchFlavour.Normal,
                                    iconCode = "fas-download",
                                    iconSize = 16),
                            SearchActionButton(
                                    name = "Merge",
                                    provider = { null },
                                    flavour = SearchFlavour.Green,
                                    iconCode = "fas-code-branch",
                                    iconSize = 16)
                    )
            ))
    )

    fun initialize() {
        vBox.onScroll = EventHandler<ScrollEvent> { event ->
            val deltaY = event.deltaY * 6
            val width = scrollPane.content.boundsInLocal.width
            scrollPane.vvalue += -deltaY / width
        }
        searchResults.children.clear()
        searchResults.children.addAll(defRes)
        searchField.textProperty().addListener { _, _, newValue ->
            if (newValue.isNotEmpty()) {
                searchResults.children.clear()
                searchResults.children.add(
                        createResultUI(SearchResult(
                                title = "Team 865",
                                header = "WARP7",
                                summary = mapOf(
                                        "Location" to "Toronto",
                                        "Event Rank" to "5"
                                ),
                                actionItems = listOf(
                                        SearchActionItem("Open in The Blue Alliance") { HBox() }
                                ),
                                status = SearchStatus("hi", SearchFlavour.Green),
                                actionButtons = listOf(
                                        SearchActionButton(
                                                name = "data",
                                                provider = { null },
                                                flavour = SearchFlavour.Green,
                                                iconCode = "fas-table",
                                                iconSize = 18)
                                ))))

            } else {
                searchResults.children.clear()
                searchResults.children.addAll(defRes)
            }
        }
    }

    fun focus() {
        Platform.runLater {
            searchField.requestFocus()
            searchField.positionCaret(searchField.text.length)
        }
    }
}
