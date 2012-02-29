#### tmplj

tmplj is a string based templating system, that aim to be lightweight and fast enough.

It does a simple translation to java source code, with compilation (requires tools.jar distributed with the jdk runtime) and runtime loading.

An example file can be seen in the test directory [example here](http://github.com/dexgeh/tmplj/blob/master/test/tmplj/test/test1.html).

Some syntax examples and java code translation:

```<import name="package.ClassName">``` &rarr; ```import package.ClassName;```

```<var type="int" name="x" expr="5">``` &rarr; ```int x = 5;```

```<set name="x" expr="10">``` &rarr; ```x = 10;```

```<do expr="x += 10">``` &rarr; ```x += 10;```

```<for expr="int i: new int[] {1,2,3,4,5}"> ... </for>``` &rarr; ```for (int i: new int[] {1,2,3,4,5}) { ... }```

```<if expr="true"> ... <elseif="true"> ... <else> ... </if>``` &rarr; ```if (true) { ... } else if (true) { ... } else { ... }```

```<print expr="i">``` &rarr; ```out.append(tmplj.util.Strings.escapeBasic(""+i);```

Due to the basic nature of the parser, writing ```<var ...>``` and ```<var .../>``` has no difference.

The compilation of templates is done by saving the generated source in a .java file, in a specified directory or, if no directory path is provided, in a temporary directory. The temporary file is compiled and, if success occurred, loaded at runtime. (see tmplj.Loader).