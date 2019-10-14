package ca.warp7.rt.ext.views

import krangl.DataFrame
import krangl.eq
import krangl.not

class ViewsModel(val initialFrame: DataFrame, selectedHeaders: List<String> = emptyList()) {

    var columnHeaders: MutableList<String> = when (selectedHeaders) {
        emptyList<String>() -> initialFrame.cols.map { it.name }
        else -> selectedHeaders
    }.toMutableList()
    val sortColumns: MutableList<SortColumn> = mutableListOf()
    val filterRows: MutableList<FilterRow> = mutableListOf()
    var colorScaleColumns: MutableList<ColorScaleColumn> = mutableListOf()

    fun mutateFrame(): DataFrame {
        var mutated = initialFrame.comboSort(sortColumns)
        filterRows.forEach { filter ->
            mutated = when (filter.whitelist) {
                true -> mutated.filter { it[filter.columnName] eq filter.value }
                false -> mutated.filter { (it[filter.columnName] eq filter.value).not() }
            }
        }
        return mutated.select(columnHeaders)
    }

    fun addSort(columns: Set<String>, sortType: SortType) {
        sortColumns.addAll(columns.map { SortColumn(sortType, it) })
        columns.forEach {
            var foundExisting = false
            sortColumns.withIndex().forEach { (i, sortColumn) ->
                if (sortColumn.columnName == it) {
                    sortColumns[i] = SortColumn(sortType, sortColumn.columnName)
                    foundExisting = true
                }
            }
            if (!foundExisting) sortColumns.add(SortColumn(sortType, it))
        }
    }

    fun addFilter(valuesByColumn: Set<Pair<String, Any>>, whitelist: Boolean) {
        filterRows.addAll(valuesByColumn.map { FilterRow(it.first, it.second, whitelist) })
    }

    fun addColorScale(columns: Set<String>, isGood: Boolean) {
        colorScaleColumns = (columns.map { ColorScaleColumn(it, isGood) } + colorScaleColumns)
                .distinctBy { it.columnName }
                .toMutableList()
    }


}
