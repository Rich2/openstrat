/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

/** Intended as a common trait for Clauses and Unclaused Statements. */
trait Clauselike

/** Statements in RCON can be unclaused or multi comma separated. The empty Clause just contains a comma. The comma at the end of the last Clause of a
 *  Statement is optional. */
case class Clause(expr: ClauseMemExpr, optComma: OptRef[CommaToken]) extends TextSpan
{ def startPosn = expr.startPosn
  def endPosn = optComma.fld(expr.endPosn, _.endPosn)
}

/** Empty Clause class, represented by just a comma. */
class EmptyClause(ct: CommaToken) extends Clause(ct, OptRef(ct)) with TextSpanCompound
{ override def startMem = ct
  override def endMem = ct
}

/** Factory object for the empty clause. Not sure if it is necessary */
object EmptyClause { def apply(ct: CommaToken): EmptyClause = new EmptyClause(ct) }