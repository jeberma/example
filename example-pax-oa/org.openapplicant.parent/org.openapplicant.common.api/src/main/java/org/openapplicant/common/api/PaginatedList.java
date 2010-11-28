package org.openapplicant.common.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class PaginatedList<E> implements List<E>{

	private List<E> list;
	
	private int currentPage;
	
	private long totalPages;
	
	private long totalEntries;
	
	public PaginatedList() {
		list = new ArrayList<E>();
	}

	public List<E> getList() {
		return list;
	}
	
	public void setList(List<E> list) {
		this.list = list;
	}
	
	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public long getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(long totalPages) {
		this.totalPages = totalPages;
	}

	public long getTotalEntries() {
		return totalEntries;
	}

	public void setTotalEntries(long totalEntries) {
		this.totalEntries = totalEntries;
	}

	@Override
	public boolean add(E o) {
		return this.list.add(o);
	}

	@Override
	public void add(int index, E element) {
		this.list.add(index, element);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		return this.list.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		return this.list.addAll(index, c);
	}

	@Override
	public void clear() {
		this.list.clear();
	}

	@Override
	public boolean contains(Object o) {
		return this.list.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return this.list.containsAll(c);
	}

	@Override
	public E get(int index) {
		return this.list.get(index);
	}

	@Override
	public int indexOf(Object o) {
		return this.list.indexOf(o);
	}

	@Override
	public boolean isEmpty() {
		return this.list.isEmpty();
	}

	@Override
	public Iterator<E> iterator() {
		return this.list.iterator();
	}

	@Override
	public int lastIndexOf(Object o) {
		return this.list.lastIndexOf(o);
	}

	@Override
	public ListIterator<E> listIterator() {
		return this.list.listIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return this.list.listIterator(index);
	}

	@Override
	public E remove(int index) {
		return this.list.remove(index);
	}

	@Override
	public boolean remove(Object o) {
		return this.list.remove(o);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return this.list.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return this.list.retainAll(c);
	}

	@Override
	public E set(int index, E element) {
		return this.list.set(index, element);
	}

	@Override
	public int size() {
		return this.list.size();
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return this.list.subList(fromIndex, toIndex);
	}

	@Override
	public Object[] toArray() {
		return this.list.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return this.list.toArray(a);
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
					.append("currentPage", currentPage)
					.append("totalPages", totalPages)
					.append("totalEntries", totalEntries)
					.append("list", list)
					.toString();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 47)
					.append(currentPage)
					.append(totalPages)
					.append(totalEntries)
					.append(list)
					.hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof PaginatedList<?>)) {
			return false;
		}
		PaginatedList<?> rhs = (PaginatedList<?>)other;
		return new EqualsBuilder()
					.append(currentPage, rhs.currentPage)
					.append(totalPages, rhs.totalPages)
					.append(totalEntries, rhs.totalEntries)
					.append(list, rhs.list)
					.isEquals();
	}
}