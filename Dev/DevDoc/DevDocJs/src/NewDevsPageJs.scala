/* Copyright 2025-6 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package docjs
import pSJs.*, pDoc.NewDevsPage

object NewDevsPageJs
{
  def main(args: Array[String]): Unit =
  { val num = NewDevsPage.inpAcc.length
    deb(s"Found $num in TomcatPage")
    NewDevsPage.inpAcc.foreach(inputUpdater => ContentUpdater(inputUpdater))
  }
} 
