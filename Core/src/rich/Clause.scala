/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich

case class Clause(expr: Expr, optComma: Option[CommaToken]) extends FileSpan
{
   def startPosn = expr.startPosn
   def endPosn = optComma.fold(expr.endPosn)(_.endPosn)
}
class EmptyClause(ct: CommaToken) extends Clause(ct, Some(ct)) with FileSpanMems
{   
   override def startMem = ct
   override def endMem = ct
}

object EmptyClause
{
   def apply(ct: CommaToken): EmptyClause = new EmptyClause(ct)
}