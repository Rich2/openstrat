package ostrat
package pParse

/** An ExprSeq can be a sequence of Statements or a Sequence of Clauses. */
trait ExprSeq extends ExprCompound
{ def exprs: Refs[Expr]
}

/** The Empty Expression has 2 subclasses the Empty Statement and the Empty Clause. The EmptyExpr can be interpreted as both a Sequence of length 0 and a None,
 * when an optional value is expected.*/
class EmptyExpr

//case class EmptyStatement(terminator: SemicolonToken)
