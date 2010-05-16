/*
 * Copyright (c) 2007, Noah Sloan <iamnoah A-T gmail D0T com> (except where otherwise noted)
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY NOAH SLOAN ``AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL NOAH SLOAN BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.noahsloan.dwr.convert;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.directwebremoting.convert.BeanConverter;
import org.directwebremoting.dwrp.ObjectOutboundVariable;
import org.directwebremoting.extend.ConverterManager;
import org.directwebremoting.extend.MarshallException;
import org.directwebremoting.extend.OutboundContext;
import org.directwebremoting.extend.OutboundVariable;
import org.directwebremoting.extend.Property;

/**
 * {@link BeanConverter} with additional template method(s) to allow more
 * control over how bean properties are converted.
 * 
 * @author noah
 * 
 */
public abstract class TemplateBeanConverter extends BeanConverter {

	/**
	 * Copy of {@link BeanConverter#convertOutbound(Object, OutboundContext)}
	 * that defers to
	 * {@link #getPropertyValue(Property, Object, OutboundContext)}.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public OutboundVariable convertOutbound(Object data, OutboundContext outctx)
			throws MarshallException {
		Map ovs = new TreeMap();

		ObjectOutboundVariable ov = new ObjectOutboundVariable(outctx);
		outctx.put(data, ov);

		try {
			for (Entry<String, Property> entry : (Set<Entry<String, Property>>) getPropertyMapFromObject(
					data, true, false).entrySet()) {
				ovs.put((String) entry.getKey(), getPropertyValue(
						(Property) entry.getValue(), data, outctx));
			}
		} catch (MarshallException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new MarshallException(data.getClass(), ex);
		}

		ov.init(ovs, getJavascript());

		return ov;
	}

	/**
	 * Template method. The default implementation uses the
	 * {@link ConverterManager} to convert the property.
	 * 
	 * @param property
	 * @param data
	 * @param outctx
	 * @return
	 * @throws MarshallException
	 */
	protected OutboundVariable getPropertyValue(Property property, Object data,
			OutboundContext outctx) throws MarshallException {
		return getConverterManager().convertOutbound(property.getValue(data),
				outctx);
	}

}
