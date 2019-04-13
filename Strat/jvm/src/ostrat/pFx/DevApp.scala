/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pFx

/** Name should possibly be DevAppFx */
object DevApp
{
  def main(args: Array[String]): Unit = javafx.application.Application.launch(classOf[AppStart], args: _*)
   
 //val pair = pDev.Apps.curr//(fromRsonFileFindElse(openStratDir / "Dev/AppNum.txt", 1)) 
}

