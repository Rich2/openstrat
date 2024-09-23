/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pFx
import utiljvm._, javafx._, stage._, scene._, canvas._, pParse._, pDev._

/** Name should possibly be DevAppFx. */
object DevApp
{
  def main(args: Array[String]): Unit = javafx.application.Application.launch(classOf[AppStart], args*)
}

class AppStart extends application.Application
{
  override def start(primaryStage: Stage): Unit =
  { val bounds = stage.Screen.getPrimary.getVisualBounds
    val canvWidth: Double = findDevSettingElse("displayWidth", bounds.getWidth - 8)
    val canvHeight = findDevSettingElse("displayHeight", bounds.getHeight)
    val canvasCanvas: Canvas = new Canvas(canvWidth, canvHeight)
    val root = new Group()
    root.getChildren.add(canvasCanvas)
    primaryStage.setX(findDevSettingElse("displayX", 0))//Sets default x value
    primaryStage.setY(findDevSettingElse("displayY", 0))//Should set y value but is not working on Linux
    val jScene = new Scene(root, canvWidth, canvHeight)
    val eExpr: EMon[pParse.AssignMemExpr] = findDevSettingExpr("appSet")

    val pair = eExpr match
    {
      case Good(it: IdentifierToken) => AppSelector.launchs.findChars(it.srcStr) match {
        case Some(launch) => {
          val fSett: EMon[FileStatements] = fileStatementsFromResource(launch.settingStr + ".rson")
          val eSett: EMon[AssignMemExpr] = fSett.goodOrOther(findDevSettingExpr(launch.settingStr))
          eSett.fold(launch.default)(launch(_))
        }
        case _ => AppSelector.ids.a1FindA2(it.srcStr) match
        { case Some(pair) => pair
          case _ => deb(it.str + ": Identifier"); AppSelector.default
        }
      }
      case Good(expr) => { debvar(expr); AppSelector.default }
      case _ => { debvar(eExpr); AppSelector.default }
    }

    val newAlt = CanvasFx(canvasCanvas, jScene)
    pair._1(newAlt)
    primaryStage.setTitle(pair._2)
    primaryStage.setScene(jScene)
    primaryStage.show
  }
}