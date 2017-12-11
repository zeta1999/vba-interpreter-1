/*******************************************************************************
 * Copyright (C) 2017 Inshua<inshua@gmail.com>
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package org.siphon.visualbasic.runtime.statements;

import org.siphon.visualbasic.ClassTypeDecl;
import org.siphon.visualbasic.Interpreter;
import org.siphon.visualbasic.SourceLocation;
import org.siphon.visualbasic.compile.JavaClassModuleDecl;
import org.siphon.visualbasic.runtime.CallFrame;
import org.siphon.visualbasic.runtime.JavaModuleInstance;
import org.siphon.visualbasic.runtime.ModuleInstance;
import org.siphon.visualbasic.runtime.Statement;
import org.siphon.visualbasic.runtime.VbRuntimeException;
import org.siphon.visualbasic.runtime.VbValue;

public class NewStatement extends Statement {

	private ClassTypeDecl classTypeDecl;

	public NewStatement(SourceLocation sourceLocation, ClassTypeDecl classTypeDecl) {
		super(sourceLocation);
		this.classTypeDecl = classTypeDecl;
	}

	@Override
	public VbValue eval(Interpreter interpreter, CallFrame frame) throws VbRuntimeException {
		ModuleInstance instance = null;
		if(classTypeDecl.classModule instanceof JavaClassModuleDecl){
			JavaClassModuleDecl jcmd = (JavaClassModuleDecl) classTypeDecl.classModule;
			instance = new JavaModuleInstance(jcmd, jcmd.newInstance(interpreter, frame, sourceLocation));
		} else {
			instance = new ModuleInstance(classTypeDecl.classModule);
			instance.initializeClass(interpreter, frame);
		} 
		return instance.asVbValue();
	}
	
	@Override
	public String toString() {
		return "New " + classTypeDecl;
	}

}
