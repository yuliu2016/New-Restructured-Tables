package ca.warp7.rt.ext.formulas.function

import ca.warp7.rt.ext.formulas.token.Ch
import ca.warp7.rt.ext.formulas.token.Expr
import ca.warp7.rt.ext.formulas.token.Name
import ca.warp7.rt.ext.formulas.token.Num
import ca.warp7.rt.ext.formulas.replaceAll
import ca.warp7.rt.ext.formulas.solve


data class ExpressionFormula(val expr: Expr, val inputArgs: List<Name>) : Formula() {
    override fun solve(args: List<Expr>): Expr {
        if (inputArgs.size != args.size) throw IllegalArgumentException("Incorrect number of arguments")

        val map: MutableMap<Name, Expr> = inputArgs.zip(args).toMap().toMutableMap()
        var solution = expr
        map.forEach { solution = solution.replaceAll(it.key, listOf(Num(it.value.solve()))) }

        return listOf(Ch('(')) + solution + listOf(Ch(')'))
    }
}