/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

/** Statements in RCON can be unclaused or multi comma separated. The empty Clause just contains a comma. The comma at the end of the last Clause of a
 *  Statement is optional. */
case class Clause(expr: ClauseMemExpr, optComma: Option[CommaToken]) extends TextSpan
{ def startPosn = expr.startPosn
  def endPosn = optComma.fld(expr.endPosn, _.endPosn)
}

/** Empty Clause class, represented by just a comma. */
class EmptyClause(ct: CommaToken) extends Clause(ct, Some(ct)) with TextSpanCompound
{ override def startMem: CommaToken = ct
  override def endMem: CommaToken = ct
}

/** Factory object for the empty clause. Not sure if it is necessary */
object EmptyClause { def apply(ct: CommaToken): EmptyClause = new EmptyClause(ct) }