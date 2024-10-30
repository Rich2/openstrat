/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pFx
import utiljvm._, javafx.*, stage.*, scene._, canvas._, pParse._, pDev._, pgui.*

/** Name should possibly be DevAppFx. */
object DevApp
{ def main(args: Array[String]): Unit = javafx.application.Application.launch(classOf[AppStart], args*)
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
    val params: java.util.List[String] = getParameters.getRaw
    val oApp: Option[String] = ife(params.isEmpty, None, Some(params.getFirst))

    /** Tries to get the app Launcher from matching the first arg. */
    val oApp2: Option[GuiLaunch] = oApp.flatMap(str => AppSelector.findChars(str))

    /** Tries to get the app launcher from matching the first arg, failing that tries to get it from the appSet setting. Else returns the default app. */
    val launch: GuiLaunch = oApp2.getOrElse(AppSelector.findErrBiCharsOrDefault(findDevSettingIdStr("appSet")))

    val pair: (CanvasPlatform => Any, String) =
    { val fSett: ThrowMon[FileStatements] = fileStatementsFromResource(launch.settingStr + ".rson")
      val eSett = fSett.succOrOther(findDevSettingExpr(launch.settingStr))
      eSett.fld(e => launch.default, launch(_))
    }

    val newAlt = CanvasFx(canvasCanvas, jScene)
    pair._1(newAlt)
    primaryStage.setTitle(pair._2)
    primaryStage.setScene(jScene)
    primaryStage.show
  }
}