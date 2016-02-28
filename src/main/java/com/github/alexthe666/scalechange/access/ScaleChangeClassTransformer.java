package com.github.alexthe666.scalechange.access;

import net.minecraft.launchwrapper.IClassTransformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class ScaleChangeClassTransformer implements IClassTransformer{

	@Override
	public byte[] transform(String name, String transformedName, byte[] classBytes) {
		{
			boolean obf;
			ClassNode classNode = new ClassNode();
			if ((obf = "boh".equals(name)) || "net.minecraft.client.renderer.entity.RendererLivingEntity".equals(name)){
				ClassReader classReader = new ClassReader(classBytes);
				classReader.accept(classNode, 0);
				String doRenderName = obf ? "a" : "doRender";
				String doRenderDesc = obf ? "(Lsv;DDDFF)V" : "(Lnet/minecraft/entity/EntityLivingBase;DDDFF)V";
				for(int i = 0; i < classNode.methods.size(); i++){
					MethodNode method = classNode.methods.get(i);
					if(doRenderName.equals(method.name) && doRenderDesc.equals(method.desc)){
						InsnList insnList = method.instructions;
						for (int j = 0; j < insnList.size(); j++) {
							{
								AbstractInsnNode insnNote = method.instructions.get(j);
								if(insnNote.getOpcode() == Opcodes.INVOKESTATIC){
									MethodInsnNode method_0 = (MethodInsnNode)insnNote;
									if(method_0.name.equals("glScalef")){
										InsnList insnList_0 = new InsnList();
										insnList_0.add(new VarInsnNode(Opcodes.ALOAD, 1));
										MethodInsnNode method_1 = new MethodInsnNode(Opcodes.INVOKESTATIC, "com/github/alexthe666/scalechange/access/ScaleChangeClientHooks", "preRenderCallBack", obf ? "(Lsv;)V" : "(Lnet/minecraft/entity/EntityLivingBase;)V", false);
										insnList_0.add(method_1);
										insnList.insert(method_0, insnList_0);
										break;
									}
								}
							}
						}
						break;

					}
				}
				ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
				classNode.accept(classWriter);
				return classWriter.toByteArray();
			}
		}
		{
			boolean obf;
			ClassNode classNode = new ClassNode();
			if ((obf = "blt".equals(name)) || "net.minecraft.client.renderer.EntityRenderer".equals(name)){
				ClassReader classReader = new ClassReader(classBytes);
				classReader.accept(classNode, 0);
				String setupCameraTransformName = obf ? "a" : "setupCameraTransform";
				String setupCameraTransformDesc = "(FI)V";
				for(int i = 0; i < classNode.methods.size(); i++){
					MethodNode method = classNode.methods.get(i);
					if(setupCameraTransformName.equals(method.name) && setupCameraTransformDesc.equals(method.desc)){
						InsnList insnList = method.instructions;
						for (int j = 0; j < insnList.size(); j++) {
							{
								AbstractInsnNode insnNote = method.instructions.get(j);
								if(insnNote.getOpcode() == Opcodes.INVOKESPECIAL){
									String methodName = "orientCamera";
									MethodInsnNode method_0 = (MethodInsnNode)insnNote;
									if(method_0.name.equals("orientCamera")){
										MethodInsnNode method_1 = new MethodInsnNode(Opcodes.INVOKESTATIC, "com/github/alexthe666/scalechange/access/ScaleChangeClientHooks", "orientCamera", "()V", false);
										insnList.set(method_0, method_1);
										break;
									}
								}
							}
						}
						break;

					}//
				}
				ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
				classNode.accept(classWriter);
				return classWriter.toByteArray();
			}
		}
		{
			boolean obf;
			ClassNode classNode = new ClassNode();
			if ((obf = "blt".equals(name)) || "net.minecraft.client.renderer.EntityRenderer".equals(name)){
				ClassReader classReader = new ClassReader(classBytes);
				classReader.accept(classNode, 0);
				String setupCameraTransformName = obf ? "a" : "setupCameraTransform";
				String setupCameraTransformDesc = "(FI)V";
				for(int i = 0; i < classNode.methods.size(); i++){
					MethodNode method = classNode.methods.get(i);
					if(setupCameraTransformName.equals(method.name) && setupCameraTransformDesc.equals(method.desc)){
						InsnList insnList = method.instructions;
						for (int j = 0; j < insnList.size(); j++) {
							{
								AbstractInsnNode insnNote = method.instructions.get(j);
								if(insnNote.getOpcode() == Opcodes.INVOKESTATIC){
									MethodInsnNode method_0 = (MethodInsnNode)insnNote;
									if(method_0.name.equals("gluPerspective")){
										MethodInsnNode method_1 = new MethodInsnNode(Opcodes.INVOKESTATIC, "com/github/alexthe666/scalechange/access/ScaleChangeClientHooks", "gluPerspectiveRender", "()V", false);
										insnList.insertBefore(method_0, method_1);
										insnList.remove(method_0);
										break;
									}
								}
							}
						}
						break;

					}
				}
				ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
				classNode.accept(classWriter);
				return classWriter.toByteArray();
			}
		}
		{
			boolean obf;
			ClassNode classNode = new ClassNode();
			if ((obf = "blt".equals(name)) || "net.minecraft.client.renderer.EntityRenderer".equals(name)){
				ClassReader classReader = new ClassReader(classBytes);
				classReader.accept(classNode, 0);
				String setupCameraTransformName = obf ? "b" : "renderHand";
				String setupCameraTransformDesc = "(FI)V";
				for(int i = 0; i < classNode.methods.size(); i++){
					MethodNode method = classNode.methods.get(i);
					if(setupCameraTransformName.equals(method.name) && setupCameraTransformDesc.equals(method.desc)){
						InsnList insnList = method.instructions;
						for (int j = 0; j < insnList.size(); j++) {
							{
								AbstractInsnNode insnNote = method.instructions.get(j);
								if(insnNote.getOpcode() == Opcodes.INVOKESTATIC){
									MethodInsnNode method_0 = (MethodInsnNode)insnNote;
									if(method_0.name.equals("gluPerspective")){
										MethodInsnNode method_1 = new MethodInsnNode(Opcodes.INVOKESTATIC, "com/github/alexthe666/scalechange/access/ScaleChangeClientHooks", "gluPerspectiveHand", "()V", false);
										insnList.insertBefore(method_0, method_1);
										insnList.remove(method_0);
										break;
									}
								}
							}
						}
						break;

					}
				}
				ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
				classNode.accept(classWriter);
				return classWriter.toByteArray();
			}
		}
		return classBytes;
	}

}
