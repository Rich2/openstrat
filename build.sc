// build.sc
import mill._, scalalib._, scalajslib._, scalanativelib._

trait Common extends ScalaModule
{ def version = "0.0.4snap"  
  def scalacOptions = Seq("-feature", "-language:higherKinds,implicitConversions", "-deprecation", "-Ywarn-value-discard", "-target:jvm-1.8", "-encoding", "UTF-8", "-unchecked", "-Xlint")
}

trait CommonStd extends Common
{ def scalaVersion = "2.13.1"
}

trait CommonStdJs extends ScalaJSModule with CommonStd
{ def scalaJSVersion = "0.6.28" 
}

trait PlatformsModule extends ScalaModule with CommonStd
{ outer =>  
  
  def sources = T.sources(millSourcePath / 'src, millSourcePath / 'jvm / 'src)

  trait InnerJs extends CommonStdJs
  { def sources = T.sources(outer.millSourcePath / 'src, millSourcePath / 'src)	  
	def ivyDeps = outer.ivyDeps() ++  Agg(ivy"org.scala-js::scalajs-dom_sjs0.6:0.9.7")
  }

  trait InnerNative extends ScalaNativeModule with CommonStd
  { def scalaVersion = "2.11.12"
    def scalaNativeVersion = "0.3.8"  
	def sources = T.sources(outer.millSourcePath / 'src, outer.millSourcePath / 'srcNat)
	def ivyDeps = outer.ivyDeps() //++ ivyNat()	 
  }

  trait InnerTests extends Tests
  { def ivyDeps = Agg(ivy"com.lihaoyi::utest:0.6.9")
    def testFrameworks = Seq("utest.runner.Framework") 
    def sources = T.sources(millSourcePath / 'src)  
  }
}

object Util extends PlatformsModule
{
  object UtilMacrosJvm extends CommonStd
  { def ivyDeps = Agg(ivy"${scalaOrganization()}:scala-reflect:${scalaVersion()}")
    def sources = T.sources(Util.millSourcePath / 'srcMacros)
  }

  object UtilMacrosJs extends CommonStdJs

  def moduleDeps = Seq(UtilMacrosJvm)
  object test extends InnerTests
  
  object js extends InnerJs
  { def moduleDeps = Seq(UtilMacrosJs)
  }

  object Nat extends InnerNative  
}

object Graphic extends PlatformsModule
{ def moduleDeps = Seq(Util)  
  object test extends InnerTests
  
  object js extends InnerJs {  def moduleDeps = Seq(Util.js)  }
  object Nat extends InnerNative
}

object World extends PlatformsModule
{ def moduleDeps = Seq(Graphic)  

  object test extends InnerTests
      
  object js extends InnerJs
  { def moduleDeps = Seq(Graphic.js) 
  }
  
  object Nat extends InnerNative
}

object Strat extends PlatformsModule
{ def moduleDeps = Seq(World)  

  object test extends InnerTests
      
  object js extends InnerJs { def moduleDeps = Seq(World.js) }
  object Nat extends InnerNative
}

object Learn extends PlatformsModule
{ def moduleDeps = Seq(Strat)  

  object test extends InnerTests
      
  object js extends InnerJs { def moduleDeps = Seq(Strat.js) }
  object Nat extends InnerNative
}

object Dev extends PlatformsModule
{ def moduleDeps = Seq(Learn)
  def mainClass = Some("ostrat.pFx.DevApp")
  def sources = T.sources(millSourcePath / 'src, millSourcePath / 'jvm / 'src)
  def resources = T.sources(millSourcePath / 'mine)

  object js extends InnerJs
  { def moduleDeps = Seq(Learn.js)
    def sources = T.sources(millSourcePath / 'src, Dev.millSourcePath / 'srcLearn)
  } 
}

def run() = Dev.runBackground()
def test = Util.test
def jsfast = Dev.js.fastOpt
def jsfull = Dev.js.fullOpt