/*
 * Copyright (c) 2007, Noah Sloan <iamnoah A-T gmail D0T com>
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

import java.util.HashMap;
import java.util.Map;

import org.directwebremoting.convert.BeanConverter;
import org.directwebremoting.extend.MarshallException;
import org.directwebremoting.extend.OutboundContext;
import org.directwebremoting.extend.OutboundVariable;
import org.directwebremoting.extend.Property;

/**
 * {@link BeanConverter} that allows for finer grained control over the
 * serialization of sub-beans. Why might you need this? Suppose you have two or
 * more classes that you want to serialize fully when each is queried
 * individually, but they also reference each other. You don't want to serialize
 * the entirety of both every time one is queried.
 * <p>
 * As an example, consider the following classes (getters and setters omitted
 * for brevity):
 * 
 * <pre>
 * class A {
 *   private B bean;
 *   ...
 * }
 * 
 * class B {
 *   private Long id;
 *   private String name;
 *   private int count;
 *   private C otherBean;
 *   ...
 * }
 * </pre>
 * 
 * Class C is not shown, but assume it is also a bean. When serializing an
 * instance of B, we want all the information about B including count and
 * otherBean to be serialized, but when serializing A, we just want to serialize
 * B.id (so we can find it later) and B.name (for rendering purposes). dwr.xml
 * would have an entry like this:
 * 
 * <pre>
 * &lt;convert converter=&quot;subBean&quot; match=&quot;A&quot;&gt;
 *   &lt;param name=&quot;subExcludes&quot;&gt;
 *     bean:otherBean,count
 *   &lt;/param&gt;
 * &lt;/convert&gt;
 * </pre>
 * 
 * Or the safer whitelisting approach:
 * 
 * <pre>
 * ...
 * &lt;param name=&quot;subIncludes&quot;&gt;
 *   bean:id,name
 * &lt;/param&gt;
 * </pre>
 * 
 * <h2>Format Notes</h2>
 * 
 * @author noah
 * 
 */
public class SubBeanConverter extends TemplateBeanConverter {

	protected Map<String, BeanConverter> subInclude = new HashMap<String, BeanConverter>();

	protected Map<String, BeanConverter> subExclude = new HashMap<String, BeanConverter>();

	@Override
	protected OutboundVariable getPropertyValue(Property property, Object data,
			OutboundContext outctx) throws MarshallException {
		Object value = property.getValue(data);
		if (value != null) {
			BeanConverter exConverter = subExclude.get(property.getName());
			if (exConverter != null) {
				return exConverter.convertOutbound(value, outctx);
			}
			BeanConverter inConverter = subInclude.get(property.getName());
			if (inConverter != null) {
				return inConverter.convertOutbound(value, outctx);
			}
		}
		return super.getPropertyValue(property, data, outctx);
	}

	public void setSubIncludes(String defs) {
		this.subInclude = parseIncludeExclude(defs, true);
	}

	private Map<String, BeanConverter> parseIncludeExclude(String defs,
			boolean include) {
		Map<String, BeanConverter> map = new HashMap<String, BeanConverter>();
		for (String def : defs.split("\\s")) {
			String[] split = def.split(":", 2);
			BeanConverter bc = new BeanConverter();
			bc.setConverterManager(getConverterManager());
			if (include) {
				bc.setInclude(split[1]);
			} else {
				bc.setExclude(split[1]);
			}
			map.put(split[0].trim(), bc);
		}
		return map;
	}

	public void setSubExcludes(String defs) {
		this.subExclude = parseIncludeExclude(defs, false);
	}

}
