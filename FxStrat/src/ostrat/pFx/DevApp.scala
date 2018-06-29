/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pFx
import scalafx.application.JFXApp

/** Needs improvement */
object DevApp  extends JFXApp
{
   val eNum: EMon[String] = eTry(io.Source.fromFile("../DevData/appNum.txt").mkString)   
   //val eNum3 = eNum.findType[Int]
   val pair = pDev.Play.curr(eNum.findTypeElse(1))
   stage = new RStage(cf => pair._1(CanvasFx(cf)), pair._2)  
}