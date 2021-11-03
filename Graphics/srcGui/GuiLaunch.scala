/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pgui
import pParse._

/** A convenience trait for launching Apps. */
trait GuiLaunch
{ def settingStr: String
  def apply(expr: Expr): (CanvasPlatform => Any, String)
  def default: (CanvasPlatform => Any, String)
}

case class GuiLaunchSimple(settingStr: String, default: (CanvasPlatform => Any, String)) extends GuiLaunch
{
  override def apply(expr: ParseExpr): (CanvasPlatform => Any, String) = default
}

trait GuiLaunchMore extends GuiLaunch
{
  override def apply(expr: ParseExpr): (CanvasPlatform => Any, String) = expr match {
    case BracketedStatements(sts, _, _, _) => fromStatments(sts)
    case _ => fromStatments(Arr())
  }

  def fromStatments(sts: Arr[Statement]): (CanvasPlatform => Any, String)
}

/** A convenience trait for launching Apps that takes an [[Int]] and an Identifier as its settings. */
trait GuiLaunchStd extends GuiLaunch
{
  def launch(s2: Int, s3: String): (CanvasPlatform => Any, String)
  override def apply(expr: Expr): (CanvasPlatform => Any, String) = expr match {
    case SpacedExpr(Arr2Tail(nd: NatDeciToken, it2: IdentifierToken, _)) => launch(nd.getInt, it2.srcStr)
    case nd: NatDeciToken => launch(nd.getInt, "")
    case _ => launch(1, "")
  }
}