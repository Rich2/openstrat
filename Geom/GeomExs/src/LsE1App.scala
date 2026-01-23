/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package learn
import ostrat.*

@main def LsE1App =
{ var continue = true
  while (continue)
  { val s = scala.io.StdIn.readLine("Enter text.\n")
    deb("You said"  -- s)
    if (s.toLowerCase == "exit") continue = false
  }
}