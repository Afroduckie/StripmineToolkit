package com.andrielgaming.bulkminer.utils;

import io.netty.util.IntSupplier;
import it.unimi.dsi.fastutil.ints.IntConsumer;
import net.minecraft.util.IntReferenceHolder;

public class FunctionalIntReferenceHolder extends IntReferenceHolder
{
	private final IntSupplier getter;
	private final IntConsumer setter;

	public FunctionalIntReferenceHolder(final IntSupplier getter, final IntConsumer setter)
	{
		this.getter = getter;
		this.setter = setter;
	}

	@Override
	public int get()
	{
		try
		{
			return this.getter.get();
		}
		catch (Exception e)
		{
			return -1;
		}
	}

	@Override
	public void set(final int value)
	{
		this.setter.accept(value);
	}
}
