/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pFx
//import scalafx.application.JFXApp

/** Name should possibly be DevAppFx */
object DevApp  extends javafx.application.Application//JFXApp
{   
   val pair = pDev.Apps.curr//(fromRsonFileFindElse(openStratDir / "Dev/AppNum.txt", 1))
   def start(stage: javafx.stage.Stage): Unit = new RStage(cf => pair._1(CanvasFxAlt(cf)), pair._2)  
}