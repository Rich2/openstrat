/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pFx
import javafx._, stage._, scene._, canvas._, pParse._, pDev._

/** Name should possibly be DevAppFx. */
object DevApp
{
  def main(args: Array[String]): Unit = javafx.application.Application.launch(classOf[AppStart], args: _*)
}

class AppStart extends application.Application
{
  override def start(primaryStage: Stage): Unit =
  {
    val bounds = stage.Screen.getPrimary.getVisualBounds
    val canvWidth: Double = findDevSettingElse("displayWidth", bounds.getWidth - 8)
    val canvHeight = findDevSettingElse("displayHeight", bounds.getHeight)
    val canvasCanvas: Canvas = new Canvas(canvWidth, canvHeight)
    val root = new Group()
    root.getChildren.add(canvasCanvas)
    primaryStage.setX(findDevSettingElse("displayX", 0))//Sets default x value
    primaryStage.setY(findDevSettingElse("displayY", 0))//Should set y value but is not working on Linux
    val jScene = new Scene(root, canvWidth, canvHeight)
    val sett: EMon[String] = findDevSettingT[String]("appStr")
    val eExpr: EMon[pParse.Expr] = findDevSettingExpr("appStr")
    val dPair: (pCanv.CanvasPlatform => Any, String) = (pWW2.WWIIGuiOld(_, pWW2.WW1940), "World War II")
    val pair = eExpr.fold{ debvar(eExpr); dPair }{expr => expr match {
      case StringToken(_, str) => Apps.theMap.getOrElse(str, dPair)
      case it: IdentifierToken if it.srcStr == "Z" => (pZug.ZugGui(_, pZug.Zug1), "JavaFx Zugfuhrer Z1 Britain")
      case SpacedExpr(Arr2(it: IdentifierToken, nd: NatDeciToken)) if Apps.idMap.contains(it.srcStr) => Apps.idMap(it.srcStr).launch(nd.getInt, "")
      case SpacedExpr(Arr2(it: IdentifierToken, _)) if Apps.idMap.contains(it.srcStr) => Apps.idMap(it.srcStr).launch(2, "")
      case SpacedExpr(exprs) => (pZug.ZugGui(_, pZug.Zug1), "JavaFx Zugfuhrer Z1 Britain")
      case _ => {debvar(expr); dPair }
    }}
    val newAlt = CanvasFx(canvasCanvas, jScene)
    pair._1(newAlt)
    primaryStage.setTitle(pair._2)
    primaryStage.setScene(jScene)
    primaryStage.show
  }
}