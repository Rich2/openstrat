/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pFx
import scalafx.application.JFXApp

/** Name should possibly be DevAppFx */
object DevApp  extends JFXApp
{   
   val pair = pDev.Apps.curr(fromRsonFileFindElse(openStratDir / "AppNum.txt", 1))
   stage = new RStage(cf => pair._1(CanvasFx(cf)), pair._2)  
}