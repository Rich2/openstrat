 /* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import cask.*

object MinimalApplication extends MainRoutes
{ @cask.get("/")
  def hello() = {
    "Hello World from Cask!"
  }

  @cask.post("/do-thing")
  def doThing(request: Request) = {
    request.text().reverse
  }

  initialize()
}