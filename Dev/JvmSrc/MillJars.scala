/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import utiljvm.*

object MillJars
{
  def main(args: Array[String]): Unit =
  {
    stagingPathDo { stagingPath =>
      stagingPath.doIfDirExists { _ =>
        deb("Mill Jars")
      }
    }
  }
}