/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pgui
import pParse._

/** A convenience trait for launching Apps. */
trait GuiLaunch
{ def settingStr: String
  def apply(expr: AssignMemExpr): (CanvasPlatform => Any, String)
  def default: (CanvasPlatform => Any, String)
}

/** The simplest launcher, not usre that this class really serves any purpose. */
case class GuiLaunchSimple(settingStr: String, default: (CanvasPlatform => Any, String)) extends GuiLaunch
{
  override def apply(expr: AssignMemExpr): (CanvasPlatform => Any, String) = default
}

/** A [[GuiLaunch]] that actually uses the setting to construct the launch." */
trait GuiLaunchMore extends GuiLaunch
{
  override def apply(expr: AssignMemExpr): (CanvasPlatform => Any, String) = expr match
  { case bls: BlockStatements => fromStatements(bls.statements)
    case _ => fromStatements(RArr())
  }

  def fromStatements(sts: RArr[Statement]): (CanvasPlatform => Any, String)
}

/** A convenience trait for launching Apps that takes an [[Int]] and an Identifier as its settings. */
trait GuiLaunchStd extends GuiLaunch
{
  def launch(s2: Int, s3: String): (CanvasPlatform => Any, String)
  override def apply(expr: AssignMemExpr): (CanvasPlatform => Any, String) = expr match {
    case SpacedExpr(Arr2Tail(nd: NatDeciToken, it2: IdentifierToken, _)) => launch(nd.getIntStd, it2.srcStr)
    case nd: NatDeciToken => launch(nd.getIntStd, "")
    case _ => launch(1, "")
  }
}