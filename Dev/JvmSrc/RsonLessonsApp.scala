/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import ostrat.utiljvm.*, pWeb.*

object RsonLessonsApp extends App
{ deb("RsonLessons App")
  
  projPathProc { path =>    
    val path1: String = path.str / "Dev/target"
    fileWrite(path1, "lessons.rson", learn.LessonsLaunch.rsonText)
  }
}