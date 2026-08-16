/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pSJs
import dless.*, prid.phex.*

object DicelessAppJs
{ def main(args: Array[String]): Unit =
    DLessGui(CanvasJs, DLessGame(DLessScen1, DLessScen1.nationSet), DLessSettings(HGView(141, 524, 20), DLessScen1.nationSet))
} 